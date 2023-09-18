package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.entity.MemberLikeStory;
import com.spring.visti.domain.member.repository.MemberLikeStoryRepository;
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
import com.spring.visti.domain.storybox.repository.StoryRepository;
import com.spring.visti.global.fcm.service.FcmService;
import com.spring.visti.global.redis.service.UrlExpiryService;
import com.spring.visti.utils.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import static com.spring.visti.utils.exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryBoxServiceImpl implements StoryBoxService {

    private final MemberRepository memberRepository;
    private final StoryBoxMemberRepository storyBoxMemberRepository;
    private final StoryBoxRepository storyBoxRepository;
    private final StoryRepository storyRePository;

    private final UrlExpiryService urlExpiryService;

    private final MemberLikeStoryRepository memberLikeStoryRepository;
    private final FcmService fcmService;

    @Override
    @Transactional
    public BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO storyBoxBuildDTO, String email){

        String storyBoxName = storyBoxBuildDTO.getName();
        if (storyBoxName == null || storyBoxName.isEmpty() || storyBoxName.length() > 20) {
            throw new ApiException(NO_STORY_BOX_NAME_ERROR);
        }

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
        String storyBoxName = storyBoxSetDTO.getName();
        if (storyBoxName == null || storyBoxName.isEmpty() || storyBoxName.length() > 20) {
            throw new ApiException(NO_STORY_BOX_NAME_ERROR);
        }

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
    public BaseResponseDTO<List<StoryBoxExposedDTO>> readMainPageStoryBoxes(String email) {

        Member member = getMember(email, memberRepository);

        List<StoryBoxMember> storyBoxes = member.getStoryBoxes();
        int forMainPage = 10;

        List<StoryBoxExposedDTO> responseStoryBox = storyBoxes.stream()
                .map(StoryBoxMember::getStoryBox)
                .sorted((sb1, sb2) -> sb2.getCreatedAt().compareTo(sb1.getCreatedAt())) // 내림차순 정렬
                .limit(forMainPage)
                .map(StoryBoxExposedDTO::of)
                .toList();

        return new BaseResponseDTO<List<StoryBoxExposedDTO>>("메인페이지 용 스토리박스 제공되었습니다.", 200, responseStoryBox) ;
    }

    @Override
    @Transactional
    public BaseResponseDTO<Page<StoryBoxExposedDTO>> readMyStoryBoxes(Pageable pageable, String email){
        Member member = getMember(email, memberRepository);

        Page<StoryBoxMember> _myStoryBoxes = storyBoxMemberRepository.findByMemberAndPosition(member, Position.HOST,pageable);

        Page<StoryBoxExposedDTO> myStoryBoxes = _myStoryBoxes
                .map(myStoryBox -> StoryBoxExposedDTO.of(myStoryBox.getStoryBox()));


        return new BaseResponseDTO<Page<StoryBoxExposedDTO>>(
                pageable.getPageNumber() +" 페이지의 내가 작성한 스토리-박스 조회가 완료되었습니다.",
                200, myStoryBoxes);
    }

    @Override
    @Transactional
    public BaseResponseDTO<Page<StoryBoxExposedDTO>> readStoryBoxes(Pageable pageable, String email){
        Member member = getMember(email, memberRepository);

        Page<StoryBoxMember> _myStoryBoxes = storyBoxMemberRepository.findByMember(member, pageable);

        Page<StoryBoxExposedDTO> myStoryBoxes = _myStoryBoxes
                .map(myStoryBox -> StoryBoxExposedDTO.of(myStoryBox.getStoryBox()));


        return new BaseResponseDTO<Page<StoryBoxExposedDTO>>(
                pageable.getPageNumber() +" 페이지의 내가 들어간 스토리-박스 조회가 완료되었습니다.",
                200, myStoryBoxes);
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
    public BaseResponseDTO<Page<StoryExposedDTO>> readStoriesInStoryBox(Pageable pageable, Long id, String email) {

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        Member member = getMember(email, memberRepository);

        List<MemberLikeStory> _memberLikeStory = member.getMemberLikedStories();
        Set<Long> likedStoryIds = _memberLikeStory.stream()
                .map(like -> like.getStory().getId())
                .collect(Collectors.toSet());

        Page<Story> _storiesInStoryBox = storyRePository.findByStoryBox(storyBox, pageable);

        Page<StoryExposedDTO> storiesInStoryBox = _storiesInStoryBox
                .map(story -> StoryExposedDTO.of(story, likedStoryIds.contains(story.getId())));


        return new BaseResponseDTO<Page<StoryExposedDTO>>(
                "스토리-박스 "+ pageable.getPageNumber() + "안의 스토리 조회가 완료되었습니다.",
                200, storiesInStoryBox);
    }

    @Override
    @Transactional
    public BaseResponseDTO<List<StoryBoxMemberListDTO>> readMemberOfStoryBox(Long id, String email) {
        Member member = getMember(email, memberRepository);

        if (!storyBoxMemberRepository.existsByStoryBoxIdAndMember(id, member)){
            throw new ApiException(UNAUTHORIZED_MEMBER_ERROR);
        }
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
    @Transactional
    public BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, String email) {

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        StoryBoxDetailDTO storyBoxInfoDTO = StoryBoxDetailDTO.toDetailDTO(storyBox);

        return new BaseResponseDTO<StoryBoxDetailDTO>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxInfoDTO);
    }

    @Override
    @Transactional
    public BaseResponseDTO<String> generateStoryBoxLink(Long id, String email) {
        Member member = getMember(email, memberRepository);

        Optional<StoryBoxMember> storyBoxMember = storyBoxMemberRepository.findByStoryBoxIdAndMember(id, member);

        if (storyBoxMember.isEmpty()){
            throw new ApiException(NO_MEMBER_ERROR);
        }

        if (Position.GUEST.equals(storyBoxMember.get().getPosition())){
            throw new ApiException(NO_AUTHORIZE_ERROR);
        }

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        String preUrlPath = "StoryBoxTokenInfo="+storyBox.getToken();
        urlExpiryService.removePreviousUrl(preUrlPath);

        // 새로운 StoryBOx URL 발급
        String token = NanoIdUtils.randomNanoId();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

        storyBox.updateToken(token, expiryDate);
        storyBoxRepository.save(storyBox);

        String urlPath = "StoryBoxTokenInfo="+token;
        String shortenedUrl = urlExpiryService.shorten(urlPath);

        return new BaseResponseDTO<String>("url Path가 발급되었습니다.", 200, "/short/"+shortenedUrl);
    }

    @Override
    @Transactional
    public BaseResponseDTO<String> validateStoryBoxLink(String token, String email) {
        Optional<StoryBox> _storyBox = storyBoxRepository.findByToken(token);

        if (_storyBox.isEmpty()){
            throw new ApiException(NO_STORY_BOX_ERROR);
        }

        if (email == null){
            return new BaseResponseDTO<>("회원가입 하게 할건가요?.", 200);
        }

        StoryBox storyBox = _storyBox.get();
        List<StoryBoxMember> _storyBoxMembers = storyBox.getStoryBoxMembers();

        boolean isMemberAlreadyJoin = _storyBoxMembers.stream()
                .map(StoryBoxMember::getMember)
                .anyMatch(member -> email.equals(member.getEmail()));

        if (isMemberAlreadyJoin){
            throw new ApiException(ALREADY_JOIN_ERROR);
        }

        String nickname = getMember(email, memberRepository).getNickname();

        _storyBoxMembers.forEach(
                member -> {
                    String fcmToken = member.getMember().getFcmToken();
                    try {
                        fcmService.sendMessageTo(fcmToken, "Visti",
                                nickname + "이 " + storyBox.getName() + "에 입장하셨습니다.",
                                "",
                                "");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        StoryBoxMember newStoryBoxMember = StoryBoxMember.joinBox(getMember(email, memberRepository), storyBox, Position.GUEST);
        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("새로운 스토리-박스에 참가하셨습니다.", 200);
    }


    @Override
    @Transactional
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
