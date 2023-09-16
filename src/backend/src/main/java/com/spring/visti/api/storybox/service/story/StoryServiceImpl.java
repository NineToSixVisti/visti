package com.spring.visti.api.storybox.service.story;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.member.repository.MemberLikeStoryRepository;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;
import com.spring.visti.domain.storybox.repository.StoryBoxMemberRepository;
import com.spring.visti.domain.storybox.repository.StoryBoxRepository;
import com.spring.visti.domain.storybox.repository.StoryRepository;

import com.spring.visti.utils.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.spring.visti.utils.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService{

    private final MemberRepository memberRepository;
    private final MemberLikeStoryRepository memberLikeStoryRepository;
    private final StoryRepository storyRepository;
    private final StoryBoxRepository storyBoxRepository;
    private final StoryBoxMemberRepository storyBoxMemberRepository;

    @Override
    @Transactional
    public BaseResponseDTO<String> createStory(StoryBuildDTO storyBuildDTO, String email) {
        Member member = getMember(email, memberRepository);

        int writtenStory = member.getDailyStoryCount();
        int canWriteStory = member.dailyStoryMaximum();
        if (!(writtenStory < canWriteStory)){ throw new ApiException(MAX_STORY_QUOTA_REACHED); }

        Optional<StoryBoxMember> storyBoxMember = storyBoxMemberRepository.findByStoryBoxIdAndMember(storyBuildDTO.getStoryBoxId(), member);
        if (storyBoxMember.isEmpty()){throw new ApiException(UNAUTHORIZED_MEMBER_ERROR);}

        StoryBox storyBox = getStoryBox(storyBuildDTO.getStoryBoxId(), storyBoxRepository);

        Story story = storyBuildDTO.toEntity(member, storyBox);
        storyRepository.save(story);

        member.updateDailyStoryCount(writtenStory+1);

        return new BaseResponseDTO<>("스토리 생성이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> createNFT4Story(Long storyId, String email) {
        return null;
    }

    @Override
    @Transactional
    public BaseResponseDTO<StoryExposedDTO> readStory(Long storyId, String email) {

        Member member = getMember(email, memberRepository);
        Story _story = getStory(storyId, storyRepository);

        List<MemberLikeStory> memberLikeStories = _story.getMembersLiked();
        boolean isMemberLikeStory = memberLikeStories.stream()
                .anyMatch(ml -> ml.getMember().getId().equals(member.getId()));

        StoryExposedDTO story = StoryExposedDTO.of(_story, isMemberLikeStory);

        return new BaseResponseDTO<StoryExposedDTO>("스토리 조회가 완료되었습니다.", 200, story);
    }


    @Override
    @Transactional
    public BaseResponseDTO<Page<StoryExposedDTO>> readMyStories(Pageable pageable, String email) {
        Member member = getMember(email, memberRepository);

        List<MemberLikeStory> _memberLikeStory = member.getMemberLikedStories();

        List<Long> likedStoryIds = _memberLikeStory.stream()
                .map(like -> like.getStory().getId())
                .toList();

        Page<Story> pagedStories = storyRepository.findByMember(member, pageable);

        Page<StoryExposedDTO> pagedMyStories = pagedStories.map(story -> StoryExposedDTO.of(story, likedStoryIds.contains(story.getId())));

        return new BaseResponseDTO<Page<StoryExposedDTO>>( pageable.getPageNumber() + "페이지 조회가 완료되었습니다.", 200, pagedMyStories);
    }

    @Override
    @Transactional
    public BaseResponseDTO<List<StoryExposedDTO>> readMainPageStories(String email) {
        Member member = getMember(email, memberRepository);

        List<Story> stories = member.getMemberStories();
        int storiesSize = stories.size();
        int forMainPage = 10;

        List<StoryExposedDTO> responseStories = new ArrayList<>();

        if (storiesSize <= forMainPage){
            Collections.shuffle(stories);

            responseStories = stories.stream()
                    .map(story -> StoryExposedDTO.of(story, true))
                    .toList();
        }else{
            responseStories = sortList4MainPage(stories, storiesSize, forMainPage);
        }

        return new BaseResponseDTO<List<StoryExposedDTO>>("메인페이지 용 셔플 스토리 제공되었습니다.", 200, responseStories);
    }

    @Override
    @Transactional
    public BaseResponseDTO<Page<StoryExposedDTO>> readLikedStories(Pageable pageable, String email) {
        Member member = getMember(email, memberRepository);

        Page<MemberLikeStory> _memberLikedStories = memberLikeStoryRepository.findByMember(member, pageable);

        Page<StoryExposedDTO> memberLikedStories = _memberLikedStories
                .map(MemberLikeStory::getStory)
                .map(LikedStory -> StoryExposedDTO.of(LikedStory, true));

//        // MemberStory 리스트에서 Story 리스트를 추출
//        List<StoryExposedDTO> stories = memberStories.stream()
//                .map(MemberLikeStory::getStory)
//                .map(LikedStory -> StoryExposedDTO.of(LikedStory, true))
//                .toList();

        return new BaseResponseDTO<Page<StoryExposedDTO>>(
                "좋아요한 스토리" +pageable.getPageNumber()+ "페이지 조회가 완료되었습니다.",
                200,
                memberLikedStories
        );
    }


    @Override
    @Transactional
    public BaseResponseDTO<String> likeStory(Long storyId, String email) {
        Member member = getMember(email, memberRepository);
        Story story = getStory(storyId, storyRepository);
        boolean isMemberLike = memberLikeStoryRepository.existsByMemberIdAndStoryId(member.getId(), storyId);

        if(isMemberLike){
            memberLikeStoryRepository.deleteByMemberIdAndStoryId(member.getId(), storyId);
            return new BaseResponseDTO<>("스토리를 '좋아요 취소' 했습니다", 200);
        }

        MemberLikeStory memberLikeThis = new MemberLikeStory().likeThis(member, story);
        memberLikeStoryRepository.save(memberLikeThis);
        return new BaseResponseDTO<>("스토리를 '좋아요' 했습니다", 200);
    }


    @Override
    @Transactional
    public BaseResponseDTO<String> deleteStory(Long storyId, String email) {

        // 사용자 조회
        Member member = getMember(email, memberRepository);

        // 스토리 조회
        Story story = getStory(storyId, storyRepository);
        Long storyWriter = story.getMember().getId();

        // 스토리 작성자가 사용자인지 조회
        if (!storyWriter.equals(member.getId())){
            throw new ApiException(UNAUTHORIZED_STORY_ERROR);
        }

        // 삭제될 경우 NFT 있는지 먼저 조회

        // NFT 있다면 NFT 소각 진행

        // DB에서 스토리 삭제
        storyRepository.delete(story);
        return new BaseResponseDTO<>("스토리 삭제가 완료되었습니다.", 200);
    }

    private List<StoryExposedDTO> sortStories(List<StoryExposedDTO> stories, String sorting_option) {
        switch (sorting_option) {
            case "ascend" -> stories.sort(Comparator.comparing(StoryExposedDTO::getCreatedAt));
            case "descend" -> stories.sort(Comparator.comparing(StoryExposedDTO::getCreatedAt).reversed());
            case "shuffle" -> Collections.shuffle(stories);
            default -> throw new IllegalArgumentException("Invalid sorting option: " + sorting_option);
        }
        return stories;
    }

    private List<StoryExposedDTO> sortList4MainPage(List<Story> stories, Integer storiesSize, Integer forMainPage) {
            if (storiesSize < 50){
                Collections.shuffle(stories);
                List<Story> selectedIndices = stories.subList(0, forMainPage);

                return selectedIndices.stream()
                        .map(story -> {
                            return StoryExposedDTO.of(story, false);
                        })
                        .toList();

            }else{

                Set<Story> selectedIndices = new HashSet<>();
                Random rand = new Random();

                while (selectedIndices.size() < forMainPage) {
                    int randomIndex = rand.nextInt(storiesSize); // 0 (inclusive) to storiesSize (exclusive)
                    selectedIndices.add(stories.get(randomIndex));
                }

                return selectedIndices.stream()
                        .map(story -> {
                            return StoryExposedDTO.of(story, false);
                        })
                        .toList();

            }
    }

}

