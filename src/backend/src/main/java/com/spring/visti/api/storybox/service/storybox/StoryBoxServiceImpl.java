package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.storybox.constant.Position;
import com.spring.visti.domain.storybox.dto.story.ResponseDTO.StoryExposedDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxSetDTO;
import com.spring.visti.domain.storybox.dto.storybox.ResponseDTO.*;
import com.spring.visti.domain.storybox.entity.Story;
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

        StoryBoxMember newStoryBoxMember = new StoryBoxMember().joinBox(member, storyBox, Position.HOST);

        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("스토리-박스 생성이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> joinStoryBox(Long storyBoxId, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);

        // 접속한 url 이 유효한지 검사



        // 이미 가입된 스토리 박스인지 확인
        List<StoryBoxMember> storyBoxes = member.getStoryBoxes();
        boolean isAlreadyJoined = storyBoxes.stream()
                .anyMatch(storyBoxMember -> storyBoxMember.getStoryBox().getId().equals(storyBoxId));

        if (isAlreadyJoined) {
            throw new ApiException(ALREADY_JOIN_ERROR);
        }

        StoryBox storyBox = getStoryBox(storyBoxId);

        StoryBoxMember newStoryBoxMember = new StoryBoxMember().joinBox(member, storyBox, Position.GUEST);

        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("새로운 스토리-박스에 참가하셨습니다.", 200);
    }

    @Override
    public BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, HttpServletRequest httpServletRequest){
        Member member = getEmail(httpServletRequest);

        StoryBox storyBox = getStoryBox(id);

        Long creatorId = storyBox.getCreator().getId();

        if (!creatorId.equals(member.getId())){
            throw new ApiException(UNAUTHORIZED_STORY_BOX_ERROR);
        }

        storyBox.updateStoryBox(storyBoxSetDTO);

        return new BaseResponseDTO<>("스토리-박스 수정이 완료되었습니다.", 200);
    }

    @Override
    public BaseResponseDTO<List<StoryBoxListDTO>> readMyStoryBoxes(HttpServletRequest httpServletRequest){
        Member member = getEmail(httpServletRequest);

        List<StoryBoxMember> _myStoryBoxes = member.getStoryBoxes();

        List<StoryBoxListDTO> myStoryBoxes = _myStoryBoxes.stream()
                .map(myStoryBox -> StoryBoxListDTO.of(myStoryBox.getStoryBox()))
                .toList();


        return new BaseResponseDTO<List<StoryBoxListDTO>>("스토리-박스 조회가 완료되었습니다.", 200, myStoryBoxes);
    }

    @Override
    public BaseResponseDTO<StoryBoxInfoDTO> readStoryBoxInfo(Long id, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);

        StoryBox storyBox = getStoryBox(id);

        StoryBoxInfoDTO storyBoxInfoDTO = StoryBoxInfoDTO.toResponse(storyBox);

        return new BaseResponseDTO<StoryBoxInfoDTO>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxInfoDTO);
    }

    @Override
    public BaseResponseDTO<List<StoryBoxStoryListDTO>> readStoriesInStoryBox(Long id, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);

        StoryBox storyBox = getStoryBox(id);

        List<Story> readStoriesInStoryBox = storyBox.getStories();

        List<MemberLikeStory> _memberLikeStory = member.getMemberLikedStories();
        List<Long> likedStoryIds = _memberLikeStory.stream()
                .map(like -> like.getStory().getId())
                .toList();

        List<StoryBoxStoryListDTO> storiesInStoryBox = readStoriesInStoryBox.stream()
                .map(story -> {
                    StoryExposedDTO storyExposed = StoryExposedDTO.of(story);

                    boolean isLiked = likedStoryIds.contains(story.getId());

                    return StoryBoxStoryListDTO.toResponse(storyExposed, isLiked);
                })
                .toList();

        return new BaseResponseDTO<List<StoryBoxStoryListDTO>>("스토리-박스 안의 스토리 조회가 완료되었습니다.", 200, storiesInStoryBox);
    }

    @Override
    public BaseResponseDTO<List<StoryBoxMemberListDTO>> readMemberOfStoryBox(Long id, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);
        StoryBox storyBox = getStoryBox(id);

        List<StoryBoxMember> _storyBoxMembers = storyBox.getStoryBoxMembers();

        //private Position position; 받아와야함...
        List<StoryBoxMemberListDTO> storyBoxMembers = _storyBoxMembers.stream()
                .map(storyBoxMember -> {

                    MemberExposedDTO memberInform = MemberExposedDTO.of(storyBoxMember.getMember());
                    Position memberPosition = storyBoxMember.getPosition();

                    return StoryBoxMemberListDTO.toResponse(memberInform, memberPosition);
                })
                .toList();

        return new BaseResponseDTO<List<StoryBoxMemberListDTO>>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxMembers);
    }

    @Override
    public BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);

        StoryBox storyBox = getStoryBox(id);

        StoryBoxDetailDTO storyBoxInfoDTO = StoryBoxDetailDTO.toDetailDTO(storyBox);

        return new BaseResponseDTO<StoryBoxDetailDTO>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxInfoDTO);
    }


    @Override
    public BaseResponseDTO<String> makeStoryBoxLink(Long id, HttpServletRequest httpServletRequest) {
        Member member = getEmail(httpServletRequest);

        return null;
    }

    @Override
    public BaseResponseDTO<String> leaveStoryBox(Long storyBoxId,HttpServletRequest httpServletRequest){
        Member member = getEmail(httpServletRequest);
        List<StoryBoxMember> storyBoxes = member.getStoryBoxes();


        Optional<StoryBoxMember> targetStoryBoxMember = storyBoxes.stream()
                .filter(storyBoxMember ->
                        storyBoxMember.getStoryBox().getId()
                                .equals(storyBoxId))
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

    public Boolean isMemberInStoryBox(Long storyBoxId, Member member){
        StoryBox storyBox = getStoryBox(storyBoxId);

        List<StoryBoxMember> membersInStoryBox = storyBox.getStoryBoxMembers();

        return membersInStoryBox.stream()
                .anyMatch(storyBoxMember ->
                        storyBoxMember.getMember().getId()
                        .equals(member.getId()));
    }

}
