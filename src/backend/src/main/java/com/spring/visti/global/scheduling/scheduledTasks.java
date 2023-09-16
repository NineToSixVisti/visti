package com.spring.visti.global.scheduling;

import com.spring.visti.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class scheduledTasks {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void resetDailyStoryCounts() {
        // 모든 회원의 dailyStoryCounts 를 0으로 설정하는 로직
        memberRepository.resetAllDailyStoryCounts();
    }
}
