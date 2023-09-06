package com.spring.visti.domain.member.repository;

import com.spring.visti.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

//    Optional<Member> findByRefreshToken(String refreshToken);
}
