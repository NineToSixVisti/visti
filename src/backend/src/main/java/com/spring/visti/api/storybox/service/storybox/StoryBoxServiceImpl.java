package com.spring.visti.api.storybox.service.storybox;

import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberExposedDTO;
import com.spring.visti.domain.member.dto.ResponseDTO.MemberStoryBoxExposedDTO;
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
import com.spring.visti.global.s3.S3UploadService;
import com.spring.visti.utils.exception.ApiException;
import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private final S3UploadService s3UploadService;

    private final MemberLikeStoryRepository memberLikeStoryRepository;
    private final FcmService fcmService;

    @Override
    @Transactional
    public BaseResponseDTO<String> createStoryBox(StoryBoxBuildDTO storyBoxBuildDTO, String email, MultipartFile multipartFile){

        String storyBoxName = storyBoxBuildDTO.getName();
        if (storyBoxName == null || storyBoxName.isEmpty() || storyBoxName.length() > 20) {
            throw new ApiException(NO_STORY_BOX_NAME_ERROR);
        }

        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

        // S3 파일 저장
        String postCategory = "storybox";
        String imageUrl;

        try {
            imageUrl = s3UploadService.S3Upload(multipartFile, postCategory);
        } catch (IOException e) {
            throw new ApiException(FILE_TYPE_ERROR);
        }

        StoryBox storyBox = storyBoxBuildDTO.toEntity(member, imageUrl);

        storyBoxRepository.save(storyBox);

        StoryBoxMember newStoryBoxMember = StoryBoxMember.joinBox(member, storyBox, Position.HOST);

        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("스토리-박스 생성이 완료되었습니다.", 200);
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BaseResponseDTO<String> enterStoryBox(Long storyBoxId, String email) {
//        Member _member = getMember(email, memberRepository);
        Member _member = getMemberBySecurity();

        if (email == null){
            throw new ApiException(NO_MEMBER_ERROR);
        }

        Optional<StoryBox> _storyBox = storyBoxRepository.findById(storyBoxId);

        if (_storyBox.isEmpty()){
            throw new ApiException(NO_STORY_BOX_ERROR);
        }

        StoryBox storyBox = _storyBox.get();

        if (storyBox.getStoryBoxMembers().size() >= 30){
            throw new ApiException(MAX_MEMBER_QUOTA_REACHED_IN_STORYBOX);
        }

        // 이미 가입된 스토리 박스인지 확인
        List<StoryBoxMember> _storyBoxMembers = storyBox.getStoryBoxMembers();

        boolean isMemberAlreadyJoin = _storyBoxMembers.stream()
                .map(StoryBoxMember::getMember)
                .anyMatch(member -> email.equals(member.getEmail()));

        if (isMemberAlreadyJoin){
            return new BaseResponseDTO<>("스토리-박스에 이미 참여 한 스토리박스입니다.", 200);
        }

        // FCM 전송
        String nickname = _member.getNickname();

        _storyBoxMembers.forEach(
                member -> {
                    String fcmToken = member.getMember().getFcmToken();
                    log.info(fcmToken + "===" + member.getMember().getEmail());
                    try {
                        fcmService.sendMessageTo(fcmToken, "Visti",
                                nickname + "이 " + storyBox.getName() + "에 입장하셨습니다.",
                                "",
                                "");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        // 방 참가 완료
        StoryBoxMember newStoryBoxMember = StoryBoxMember.joinBox(_member, storyBox, Position.GUEST);
        storyBoxMemberRepository.save(newStoryBoxMember);

        return new BaseResponseDTO<>("스토리-박스에 참가하셨습니다.", 200);
    }

    @Override
    @Transactional
    public BaseResponseDTO<String> setStoryBox(Long id, StoryBoxSetDTO storyBoxSetDTO, String email, MultipartFile multipartFile) throws IOException {
        String storyBoxName = storyBoxSetDTO.getName();
        if (storyBoxName == null || storyBoxName.isEmpty() || storyBoxName.length() > 20) {
            throw new ApiException(NO_STORY_BOX_NAME_ERROR);
        }

        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        Long creatorId = storyBox.getCreator().getId();

        if (!creatorId.equals(member.getId())){
            throw new ApiException(UNAUTHORIZED_STORY_BOX_ERROR);
        }

        // S3 파일 저장
        String postCategory = "storybox";
        String imageUrl;
        // 스토리박스 이전 사진 삭제(이전 사진이 있을 경우만)
        String originImagePath = storyBox.getBoxImgPath();
        if (originImagePath.length() > 0){
            int s3DomainLastIndex = originImagePath.indexOf(".com/") + 5;
            if (s3DomainLastIndex > 0) {
                String pathWithFilename = originImagePath.substring(s3DomainLastIndex);
                s3UploadService.deleteS3File(pathWithFilename);
            }
        }
        try {
            imageUrl = s3UploadService.S3Upload(multipartFile, postCategory);
        } catch (IOException e) {
            throw new ApiException(FILE_TYPE_ERROR);
        }

        storyBox.updateStoryBox(storyBoxSetDTO, imageUrl);

        return new BaseResponseDTO<>("스토리-박스 수정이 완료되었습니다.", 200);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO<List<StoryBoxExposedDTO>> readMainPageStoryBoxes(String email) {

        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

        List<StoryBoxMember> storyBoxes = member.getStoryBoxes();
        int forMainPage = 10;

        LocalDateTime localDateTime = LocalDateTime.now();

        List<StoryBoxExposedDTO> responseStoryBox = storyBoxes.stream()
                .map(StoryBoxMember::getStoryBox)
                .filter(storyBox -> storyBox.getFinishedAt().isAfter(localDateTime))
                .sorted((sb1, sb2) -> sb1.getFinishedAt().compareTo(sb2.getFinishedAt())) // 내림차순 정렬
                .limit(forMainPage)
                .map(StoryBoxExposedDTO::of)
                .toList();

        return new BaseResponseDTO<List<StoryBoxExposedDTO>>("메인페이지 용 스토리박스 제공되었습니다.", 200, responseStoryBox) ;
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO<Page<StoryBoxExposedDTO>> readMyStoryBoxes(Pageable pageable, String email){
        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

        Page<StoryBoxMember> _myStoryBoxes = storyBoxMemberRepository.findByMemberAndPosition(member, Position.HOST, pageable);

        Page<StoryBoxExposedDTO> myStoryBoxes = _myStoryBoxes
                .map(myStoryBox -> StoryBoxExposedDTO.of(myStoryBox.getStoryBox()));


        return new BaseResponseDTO<Page<StoryBoxExposedDTO>>(
                pageable.getPageNumber() +" 페이지의 내가 작성한 스토리-박스 조회가 완료되었습니다.",
                200, myStoryBoxes);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO<Page<StoryBoxExposedDTO>> readStoryBoxes(Pageable pageable, String email){
        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

        Page<StoryBoxMember> _myStoryBoxes = storyBoxMemberRepository.findByMember(member, pageable);

        Page<StoryBoxExposedDTO> myStoryBoxes = _myStoryBoxes
                .map(myStoryBox -> StoryBoxExposedDTO.of(myStoryBox.getStoryBox()));


        return new BaseResponseDTO<Page<StoryBoxExposedDTO>>(
                pageable.getPageNumber() +" 페이지의 내가 들어간 스토리-박스 조회가 완료되었습니다.",
                200, myStoryBoxes);
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<Page<StoryBoxExposedDTO>> searchStoryBoxes(Pageable pageable, String email, String keyword) {
        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();
        Page<StoryBox> storyBoxMembers = storyBoxMemberRepository.findJoinedByMemberAndKeyword(member,keyword,pageable);
        Page<StoryBoxExposedDTO> searchStoryBoxes = storyBoxMembers
                .map(storyBoxMember -> StoryBoxExposedDTO.of(storyBoxMember));

        return new BaseResponseDTO<Page<StoryBoxExposedDTO>>(
                pageable.getPageNumber() + "페이지의 검색결과 조회가 완료되었습니다.",
                200, searchStoryBoxes);
    }


    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO<StoryBoxInfoDTO> readStoryBoxInfo(Long id, String email) {
        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        Member creator = storyBox.getCreator();

        boolean isHost = Objects.equals(member.getId(), creator.getId());

        StoryBoxInfoDTO storyBoxInfoDTO = StoryBoxInfoDTO.toResponse(storyBox, isHost);

        return new BaseResponseDTO<StoryBoxInfoDTO>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxInfoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO<Page<StoryExposedDTO>> readStoriesInStoryBox(Pageable pageable, Long id, String email) {

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

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
    @Transactional(readOnly = true)
    public BaseResponseDTO<List<MemberStoryBoxExposedDTO>> readMemberOfStoryBox(Long id, String email) {
        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

        if (!storyBoxMemberRepository.existsByStoryBoxIdAndMember(id, member)){
            throw new ApiException(UNAUTHORIZED_MEMBER_ERROR);
        }
        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        List<StoryBoxMember> _storyBoxMembers = storyBox.getStoryBoxMembers();

        //private Position position; 받아와야함...
        List<MemberStoryBoxExposedDTO> storyBoxMembers = _storyBoxMembers.stream()
                .map(storyBoxMember -> MemberStoryBoxExposedDTO
                        .toResponse(storyBoxMember.getMember(), storyBoxMember.getPosition()))
                .toList();

        return new BaseResponseDTO<List<MemberStoryBoxExposedDTO>>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxMembers);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseResponseDTO<StoryBoxDetailDTO> readStoryBoxDetail(Long id, String email) {

        StoryBox storyBox = getStoryBox(id, storyBoxRepository);

        StoryBoxDetailDTO storyBoxInfoDTO = StoryBoxDetailDTO.toDetailDTO(storyBox);

        return new BaseResponseDTO<StoryBoxDetailDTO>("스토리-박스 조회가 완료되었습니다.", 200, storyBoxInfoDTO);
    }

    @Transactional(readOnly = true)
    public BaseResponseDTO<StoryBoxExposedDTO> readLatestStoryBoxes(String email) {
        Member member = getMember(email, memberRepository);

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime fourDaysLater = localDateTime.plusDays(4);

        List<StoryBox> myStoryBoxes = member.getStoryBoxes().stream()
                .map(StoryBoxMember::getStoryBox).toList();

        StoryBox _latestFutureStoryBox = myStoryBoxes.stream()
                .filter(storyBox -> {
                    return !storyBox.getFinishedAt().isBefore(localDateTime)
                            && !storyBox.getFinishedAt().isAfter(fourDaysLater);
                })
                .min(Comparator.comparing(StoryBox::getFinishedAt))
                .orElse(null);



        if (_latestFutureStoryBox != null){
            StoryBoxExposedDTO latestFutureStoryBox = StoryBoxExposedDTO.of(_latestFutureStoryBox);
            return new BaseResponseDTO<StoryBoxExposedDTO>("가장 가까운 미래에 닫힐 Story Box 조회가 완료되었습니다",200 , latestFutureStoryBox);
        }

        return new BaseResponseDTO<StoryBoxExposedDTO>("가장 가까운 미래에 닫힐 Story Box 조회가 완료되었습니다",200 , null);

    }

    @Override
    @Transactional
    public BaseResponseDTO<String> generateStoryBoxLink(Long id, String email) {
        Member member = getMember(email, memberRepository);
//        Member member = getMemberBySecurity();

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

        return new BaseResponseDTO<String>("url Path가 발급되었습니다.", 200, "/visti/"+shortenedUrl);
    }

    @Override
    @Transactional
    public BaseResponseDTO<String> validateStoryBoxLink(String token, String email) {
        
        if (email == null){
            return new BaseResponseDTO<>("회원가입 하게 할건가요?.", 200);
        }

        Optional<StoryBox> _storyBox = storyBoxRepository.findByToken(token);

        if (_storyBox.isEmpty()){
            throw new ApiException(NO_STORY_BOX_ERROR);
        }

        if (_storyBox.get().getStoryBoxMembers().size() >= 30){
            throw new ApiException(MAX_MEMBER_QUOTA_REACHED_IN_STORYBOX);
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
                    log.info(fcmToken + "===" + member.getMember().getEmail());
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
//        Member member = getMemberBySecurity();
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
