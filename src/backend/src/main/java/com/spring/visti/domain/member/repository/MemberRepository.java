package com.spring.visti.domain.member.repository;

import com.spring.visti.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT m FROM Member m WHERE " +
            "(m.role = 'USER' AND m.dailyStoryCount < 5) OR " +
            "(m.role = 'SUBSCRIBER' AND m.dailyStoryCount < 6)")
        List<Member> findMembersWithDailyStoryCountLessThanMaximum();

    @Modifying
    @Query("UPDATE Member m SET m.dailyStoryCount = 0")
    void resetAllDailyStoryCounts();
//    Optional<Member> findByRefreshToken(String refreshToken);
}
