package com.spring.demo.api.storybox.service.story;

import com.spring.demo.api.dto.BaseResponseDTO;
import com.spring.demo.domain.member.entity.Member;
import com.spring.demo.domain.member.entity.MemberLikeStory;
import com.spring.demo.domain.member.repository.MemberRepository;
import com.spring.demo.domain.member.repository.MemberStoryRepository;
import com.spring.demo.domain.storybox.dto.story.StoryBuildDTO;
import com.spring.demo.domain.storybox.entity.Story;
import com.spring.demo.domain.storybox.repository.StoryRepository;
import com.spring.demo.global.jwt.service.TokenProvider;

import com.spring.demo.utils.exception.ApiException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spring.demo.utils.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService{

    private final MemberRepository memberRepository;
    private final MemberStoryRepository memberStoryRepository;
    private final StoryRepository storyRepository;
    private final TokenProvider tokenProvider;
    @Override
    public BaseResponseDTO<String> createStory(StoryBuildDTO storyBuildDTO, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);

        Story story = storyBuildDTO.toEntity(member);
        storyRepository.save(story);

        return new BaseResponseDTO<>("스토리 생성이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> createNFT4Story(Long storyId, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public BaseResponseDTO<Story> readStory(Long storyId) {

        Story story = getStory(storyId);

        return new BaseResponseDTO<Story>("스토리 생성이 완료되었습니다.", 200, story);
    }

    @Override
    public BaseResponseDTO<List<Story>> readMyStories(HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);

        List<Story> myStories = member.getMemberStories();
        return new BaseResponseDTO<List<Story>>("자신이 작성한 스토리 조회가 완료되었습니다.", 200, myStories);
    }

    @Override
    public BaseResponseDTO<String> likeStory(Long storyId, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);
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
    public BaseResponseDTO<List<Story>> readLikedStories(HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);
        List<MemberLikeStory> memberStories = member.getMemberLikedStories();

        // MemberStory 리스트에서 Story 리스트를 추출
        List<Story> stories = memberStories.stream()
                .map(MemberLikeStory::getStory)
                .toList();

        return new BaseResponseDTO<List<Story>>("좋아요한 스토리 조회가 완료되었습니다.", 200, stories);
    }

    @Override
    public BaseResponseDTO<String> deleteStory(Long storyId, HttpServletRequest httpServletRequest) {

        // 사용자 조회
        Member member = getEmail(httpServletRequest);

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

    public Member getEmail(HttpServletRequest httpServletRequest) {

        String email = tokenProvider.getHeaderToken(httpServletRequest, "Access");

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

