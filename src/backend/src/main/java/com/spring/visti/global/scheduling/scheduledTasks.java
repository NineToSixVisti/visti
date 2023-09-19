package com.spring.visti.global.scheduling;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.member.repository.MemberRepository;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.domain.storybox.entity.StoryBoxMember;
import com.spring.visti.domain.storybox.repository.StoryBoxRepository;
import com.spring.visti.global.fcm.service.FcmService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class scheduledTasks {

    private final StoryBoxRepository storyBoxRepository;
    private final MemberRepository memberRepository;
    private final FcmService fcmService;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void resetDailyStoryCounts() {
        // 모든 회원의 dailyStoryCounts 를 0으로 설정하는 로직
        memberRepository.resetAllDailyStoryCounts();
    }

    @Transactional
    @Scheduled(cron = "0 0 22 * * ?")
    public void sendMessageToUserAt22Clock() { // 매일 22시에 실행
        // 모든 회원 중 dailyStoryCounts 가 채워지지 않은 사람들에게 메시지 보냄

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

    @Scheduled(cron = "0 0 18 * * ?")
    public void sendMessageBeforeClose() { // 매일 18시에 실행
        // 모슽 스토리 박스들 중 해당 날짜에 종료가 되는 스토리박스에게 알림이감

        LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        List<StoryBox> storyBoxesClosingToday = storyBoxRepository.findByFinishedAtBetween(startOfToday, endOfToday);
        storyBoxesClosingToday
                .forEach(storyBox -> {
                    List<Member> members = storyBox.getStoryBoxMembers().stream()
                            .map(StoryBoxMember::getMember).toList();

                    members
                            .forEach(member -> {
                                String fcmToken = member.getFcmToken();

                                try {
                                    fcmService.sendMessageTo(fcmToken,
                                            "Visti",
                                            "오늘 자정에 " + storyBox.getName() + "가 종료됩니다. 마지막 기회를 놓치지 마세요!",
                                            "",
                                            "");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                    });
                });
    }

}
