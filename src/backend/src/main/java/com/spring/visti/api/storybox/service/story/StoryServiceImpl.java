package com.spring.visti.api.storybox.service.story;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.member.repository.MemberStoryRepository;
import com.spring.visti.domain.report.repository.ReportRepository;
import com.spring.visti.domain.storybox.dto.story.RequestDTO.StoryBuildDTO;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.entity.Story;
import com.spring.visti.domain.storybox.repository.StoryBoxRepository;
import com.spring.visti.domain.storybox.repository.StoryRepository;
import com.spring.visti.global.jwt.service.TokenProvider;

import com.spring.visti.utils.exception.ApiException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spring.visti.utils.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService{

    private final MemberRepository memberRepository;
    private final MemberStoryRepository memberStoryRepository;
    private final StoryRepository storyRepository;


    @Override
    public BaseResponseDTO<String> createStory(StoryBuildDTO storyBuildDTO, String email) {
        Member member = getMember(email);

        boolean canWriteStory = member.dailyStoryCount();
        if (!canWriteStory){ throw new ApiException(MAX_STORY_QUOTA_REACHED); }

        Story story = storyBuildDTO.toEntity(member);
        storyRepository.save(story);

        return new BaseResponseDTO<>("스토리 생성이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> createNFT4Story(Long storyId, String email) {
        return null;
    }

    @Override
    public BaseResponseDTO<List<StoryExposedDTO>> readMyStories(String email) {
        Member member = getMember(email);
        List<MemberLikeStory> _memberLikeStory = member.getMemberLikedStories();

        List<Long> likedStoryIds = _memberLikeStory.stream()
                .map(like -> like.getStory().getId())
                .toList();


        List<Story> _myStories = member.getMemberStories();
        List<StoryExposedDTO> myStories = _myStories.stream()
                .map(story -> StoryExposedDTO.of(story, likedStoryIds.contains(story.getId())))
                .toList();

        return new BaseResponseDTO<List<StoryExposedDTO>>("작성한 스토리 조회가 완료되었습니다.", 200, myStories);
    }

    @Override
    public BaseResponseDTO<StoryExposedDTO> readStory(Long storyId, String email) {

        Member member = getMember(email);
        Story _story = getStory(storyId);

        List<MemberLikeStory> mls = _story.getMembersLiked();
        boolean isMemberLikeStory = mls.stream()
                .anyMatch(ml -> ml.getMember().getId().equals(member.getId()));

        StoryExposedDTO story = StoryExposedDTO.of(_story, isMemberLikeStory);

        return new BaseResponseDTO<StoryExposedDTO>("스토리 조회가 완료되었습니다.", 200, story);
    }


    @Override
    public BaseResponseDTO<String> likeStory(Long storyId, String email) {
        Member member = getMember(email);
        Story story = getStory(storyId);
        Optional<MemberLikeStory> isMemberLike = memberStoryRepository.findByMemberAndStory(member, story);

        if(isMemberLike.isPresent()){
            memberStoryRepository.delete(isMemberLike.get());
            return new BaseResponseDTO<>("스토리를 '좋아요 취소' 했습니다", 200);
        }

        MemberLikeStory memberLikeThis = new MemberLikeStory().likeThis(member, story);
        memberStoryRepository.save(memberLikeThis);
        return new BaseResponseDTO<>("스토리를 '좋아요' 했습니다", 200);
    }

    @Override
    public BaseResponseDTO<List<StoryExposedDTO>> readLikedStories(String email) {
        Member member = getMember(email);
        List<MemberLikeStory> memberStories = member.getMemberLikedStories();

        // MemberStory 리스트에서 Story 리스트를 추출
        List<StoryExposedDTO> stories = memberStories.stream()
                .map(MemberLikeStory::getStory)
                .map(LikedStory -> StoryExposedDTO.of(LikedStory, true))
                .toList();

        return new BaseResponseDTO<List<StoryExposedDTO>>("좋아요한 스토리 조회가 완료되었습니다.", 200, stories);
    }



    @Override
    public BaseResponseDTO<String> deleteStory(Long storyId, String email) {

        // 사용자 조회
        Member member = getMember(email);

        // 스토리 조회
        Story story = getStory(storyId);
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


    public Member getMember(String email) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isEmpty()){ throw new ApiException(NO_MEMBER_ERROR); }

        return optionalMember.get();
    }

    public Story getStory(Long storyId) {

        Optional<Story> optionalStory = storyRepository.findById(storyId);

        if (optionalStory.isEmpty()){ throw new ApiException(NO_STORY_ERROR); }

        return optionalStory.get();
    }
}

