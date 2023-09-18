package com.spring.visti.global.scheduling;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.global.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class scheduledTasks {

    private final MemberRepository memberRepository;
    private final FcmService fcmService;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void resetDailyStoryCounts() {
        // 모든 회원의 dailyStoryCounts 를 0으로 설정하는 로직
        memberRepository.resetAllDailyStoryCounts();
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void sendMessageToUserAt22Clock() { // 매일 22시에 실행
        // 모든 회원 중 dailyStoryCounts 가 체워지지 않은 사람들에게 메시지 보냄

        List<Member> members = memberRepository.findMembersWithDailyStoryCountLessThanMaximum();
        members
                .forEach(member -> {
                    String fcmToken = member.getFcmToken();

                    try {
                        fcmService.sendMessageTo(fcmToken,
                                "Visti",
                                "오늘 하루가 2시간 밖에 남지 않았어요! 오늘 어땠는지 알려주세요.",
                                "",
                                "");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });

    }

}
