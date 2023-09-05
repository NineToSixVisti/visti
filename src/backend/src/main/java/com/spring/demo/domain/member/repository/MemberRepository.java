package com.spring.demo.domain.member.repository;

import com.spring.demo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

//    Optional<Member> findByRefreshToken(String refreshToken);
}
