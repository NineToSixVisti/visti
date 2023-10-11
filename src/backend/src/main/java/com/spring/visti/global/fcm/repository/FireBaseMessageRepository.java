package com.spring.visti.global.fcm.repository;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.global.fcm.entity.FireBaseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireBaseMessageRepository extends JpaRepository<FireBaseMessage, Long> {
    List<FireBaseMessage> findByMember(Member member);
}
