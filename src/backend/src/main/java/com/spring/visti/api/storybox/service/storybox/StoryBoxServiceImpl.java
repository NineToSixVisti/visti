package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.MemberJoinDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxInfoDTO;
import com.spring.visti.domain.storybox.dto.storybox.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;
import com.spring.visti.domain.storybox.repository.StoryBoxMemberRepository;
import com.spring.visti.domain.storybox.repository.StoryBoxRepository;
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
public class StoryBoxServiceImpl implements StoryBoxService {
    private final MemberRepository memberRepository;
    private final StoryBoxMemberRepository storyBoxMemberRepository;
    private final StoryBoxRepository storyBoxRepository;
    private final TokenProvider tokenProvider;

    @Override
    public BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO storyBoxBuildDTO, HttpServletRequest httpServletRequest){
        Member member = getEmail(httpServletRequest);

        StoryBox storyBox = storyBoxBuildDTO.toEntity(member);

        storyBoxRepository.save(storyBox);

        return new BaseResponseDTO<>("스토리-박스 생성이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> joinStoryBox(Long storyBoxId, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);
        List<StoryBoxMember> storyBoxes = member.getStoryBoxes();

        // 이미 가입된 스토리 박스인지 확인
        boolean isAlreadyJoined = storyBoxes.stream()
                .anyMatch(storyBoxMember -> storyBoxMember.getStoryBox().getId().equals(storyBoxId));

        if (isAlreadyJoined) {
            throw new ApiException(ALREADY_JOIN_ERROR);
        }

        StoryBox storyBox = getStoryBox(storyBoxId);

        StoryBoxMember newStoryBoxMember = new StoryBoxMember().joinBox(member, storyBox);

        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("새로운 스토리-박스에 참가하셨습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, HttpServletRequest httpServletRequest){
        Member member = getEmail(httpServletRequest);

        StoryBox storyBox = getStoryBox(id);

        Long creatorId = storyBox.getId();

        if (!creatorId.equals(member.getId())){
            throw new ApiException(UNAUTHORIZED_STORY_BOX_ERROR);
        }

        storyBox.updateStoryBox(storyBoxSetDTO);

        return  new BaseResponseDTO<>("스토리-박스 수정이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> readStoryBoxInfo(StoryBoxInfoDTO memberInfo) {
        return null;
    }

    @Override
    public BaseResponseDTO<String> readMemberOfStoryBox(MemberJoinDTO memberInfo) {
        return null;
    }

    @Override
    public BaseResponseDTO<String> readMyStoryBoxes(MemberJoinDTO memberInfo){

        return null;
    }

    @Override
    public BaseResponseDTO<String> leaveStoryBox(Long storyBoxId,HttpServletRequest httpServletRequest){
        Member member = getEmail(httpServletRequest);
        List<StoryBoxMember> storyBoxes = member.getStoryBoxes();


        Optional<StoryBoxMember> targetStoryBoxMember = storyBoxes.stream()
                .filter(storyBoxMember -> storyBoxMember.getStoryBox().getId().equals(storyBoxId))
                .findFirst();

        if(targetStoryBoxMember.isPresent()) {
            storyBoxMemberRepository.delete(targetStoryBoxMember.get());
            return new BaseResponseDTO<>("성공적으로 스토리 박스를 나갔습니다.", 200);
        } else {
            throw new ApiException(NO_STORY_BOX_ERROR);
        }
    }

    public Member getEmail(HttpServletRequest httpServletRequest) {

        String email = tokenProvider.getHeaderToken(httpServletRequest, "Access");

        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isEmpty()){ throw new ApiException(NO_MEMBER_ERROR); }

        return optionalMember.get();
    }

    public StoryBox getStoryBox(Long storyBoxId) {

        Optional<StoryBox> optionalStoryBox = storyBoxRepository.findById(storyBoxId);

        if (optionalStoryBox.isEmpty()){ throw new ApiException(NO_STORY_BOX_ERROR); }

        return optionalStoryBox.get();
    }

}
