package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.common.dto.BaseResponseDTO;
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
import com.spring.visti.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.spring.visti.utils.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryBoxServiceImpl implements StoryBoxService {
    private final MemberRepository memberRepository;
    private final StoryBoxMemberRepository storyBoxMemberRepository;
    private final StoryBoxRepository storyBoxRepository;

    @Override
    @Transactional
    public BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO storyBoxBuildDTO, String email){
        Member member = getMember(email, memberRepository);

        StoryBox storyBox = storyBoxBuildDTO.toEntity(member);

        storyBoxRepository.save(storyBox);

        StoryBoxMember newStoryBoxMember = StoryBoxMember.joinBox(member, storyBox, Position.HOST);

        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("스토리-박스 생성이 완료되었습니다.", 200);
    }

    @Override
    @Transactional
    public BaseResponseDTO<String> enterStoryBox(Long storyBoxId, String email) {
        Member member = getMember(email, memberRepository);

        // 이미 가입된 스토리 박스인지 확인
        List<StoryBoxMember> storyBoxes = member.getStoryBoxes();
        boolean isAlreadyJoined = storyBoxes.stream()
                .anyMatch(storyBoxMember -> storyBoxMember.getStoryBox().getId().equals(storyBoxId));

        if (!isAlreadyJoined) {
            throw new ApiException(UNAUTHORIZED_MEMBER_ERROR);
        }

        return new BaseResponseDTO<>("스토리-박스에 참가하셨습니다.", 200);
    }

    @Override
    @Transactional
    public BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, String email){
        Member member = getMember(email, memberRepository);

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        Long creatorId = storyBox.getCreator().getId();

        if (!creatorId.equals(member.getId())){
            throw new ApiException(UNAUTHORIZED_STORY_BOX_ERROR);
        }

        storyBox.updateStoryBox(storyBoxSetDTO);

        return new BaseResponseDTO<>("스토리-박스 수정이 완료되었습니다.", 200);
    }

    @Override
    @Transactional
    public BaseResponseDTO<List<StoryBoxListDTO>> readMyStoryBoxes(String email){
        Member member = getMember(email, memberRepository);

        List<StoryBoxMember> _myStoryBoxes = member.getStoryBoxes();

        List<StoryBoxListDTO> myStoryBoxes = _myStoryBoxes.stream()
                .map(myStoryBox -> StoryBoxListDTO.of(myStoryBox.getStoryBox()))
                .toList();


        return new BaseResponseDTO<List<StoryBoxListDTO>>("스토리-박스 조회가 완료되었습니다.", 200, myStoryBoxes);
    }

    @Override
    @Transactional
    public BaseResponseDTO<StoryBoxInfoDTO> readStoryBoxInfo(Long id, String email) {
        Member member = getMember(email, memberRepository);

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        StoryBoxInfoDTO storyBoxInfoDTO = StoryBoxInfoDTO.toResponse(storyBox);

        return new BaseResponseDTO<StoryBoxInfoDTO>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxInfoDTO);
    }

    @Override
    @Transactional
    public BaseResponseDTO<List<StoryExposedDTO>> readStoriesInStoryBox(Long id, String email) {

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);
        List<Story> readStoriesInStoryBox = storyBox.getStories();

        Member member = getMember(email, memberRepository);
        List<MemberLikeStory> _memberLikeStory = member.getMemberLikedStories();
        List<Long> likedStoryIds = _memberLikeStory.stream()
                .map(like -> like.getStory().getId())
                .toList();

        List<StoryExposedDTO> storiesInStoryBox = readStoriesInStoryBox.stream()
                .map(story -> StoryExposedDTO.of(story, likedStoryIds.contains(story.getId())))
                .toList();

        return new BaseResponseDTO<List<StoryExposedDTO>>("스토리-박스 안의 스토리 조회가 완료되었습니다.", 200, storiesInStoryBox);
    }

    @Override
    @Transactional
    public BaseResponseDTO<List<StoryBoxMemberListDTO>> readMemberOfStoryBox(Long id, String email) {
        Member member = getMember(email, memberRepository);
        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

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
    public BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, String email) {


        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        StoryBoxDetailDTO storyBoxInfoDTO = StoryBoxDetailDTO.toDetailDTO(storyBox);

        return new BaseResponseDTO<StoryBoxDetailDTO>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxInfoDTO);
    }

    @Override
    public BaseResponseDTO<String> generateStoryBoxLink(Long id, String email) {
        Member member = getMember(email, memberRepository);
        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        Optional<StoryBoxMember> storyBoxMember = storyBoxMemberRepository.findByStoryBoxAndMember(storyBox, member);

        if (storyBoxMember.isEmpty()){
            throw new ApiException(NO_MEMBER_ERROR);
        }

        if (Position.GUEST.equals(storyBoxMember.get().getPosition())){
            throw new ApiException(NO_AUTHORIZE_ERROR);
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

        storyBox.updateToken(token, expiryDate);
        storyBoxRepository.save(storyBox);

        String urlPath = "/validate?token="+token;

        return new BaseResponseDTO<String>("토큰이 발급되었습니다.", 200, token);
    }

    @Override
    public BaseResponseDTO<String> validateStoryBoxLink(String token, String email) {
        Optional<StoryBox> _storyBox = storyBoxRepository.findByToken(token);

        if (_storyBox.isEmpty()){
            throw new ApiException(NO_STORY_BOX_ERROR);
        }

        StoryBox storyBox = _storyBox.get();
        LocalDateTime expiryDate = storyBox.getExpireTime();

        if (LocalDateTime.now().isAfter(expiryDate)){
            throw new ApiException(NO_INVITATION_LINK);
        }

        if (email == null){
            return new BaseResponseDTO<>("회원가입 하게 할건가요?.", 200);
        }

        List<StoryBoxMember> _storyBoxMembers = storyBox.getStoryBoxMembers();

        boolean isMemberAlreadyJoin = _storyBoxMembers.stream()
                .map(StoryBoxMember::getMember)
                .anyMatch(member -> email.equals(member.getEmail()));

        if (isMemberAlreadyJoin){
            throw new ApiException(ALREADY_JOIN_ERROR);
        }

        StoryBoxMember newStoryBoxMember = StoryBoxMember.joinBox(getMember(email, memberRepository), storyBox, Position.HOST);
        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("새로운 스토리-박스에 참가하셨습니다.", 200);
    }


    @Override
    public BaseResponseDTO<String> leaveStoryBox(Long storyBoxId, String email){
        Member member = getMember(email, memberRepository);
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


    public Boolean isMemberInStoryBox(Long storyBoxId, Member member){
        StoryBox storyBox = getStoryBox(storyBoxId, storyBoxRepository);

        List<StoryBoxMember> membersInStoryBox = storyBox.getStoryBoxMembers();

        return membersInStoryBox.stream()
                .anyMatch(storyBoxMember ->
                        storyBoxMember.getMember().getId()
                        .equals(member.getId()));
    }

}
