package com.spring.visti.api.external.service;


import com.spring.visti.api.common.dto.BaseResponseDTO;
import com.spring.visti.api.common.service.DefaultService;
import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.global.fcm.entity.FireBaseMessage;
import com.spring.visti.global.fcm.repository.FireBaseMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class fcmTokenService implements DefaultService {

    private final FireBaseMessageRepository fireBaseMessageRepository;

    @Transactional
    public BaseResponseDTO<List<FireBaseMessage>> getMessage(){

        Member member = getMemberBySecurity();
        List<FireBaseMessage> messages = fireBaseMessageRepository.findByMember(member);

        return new BaseResponseDTO<List<FireBaseMessage>>("알림 메시지를 제공합니다.", 200, messages);
    }
}
