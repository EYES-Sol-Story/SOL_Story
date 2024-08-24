package com.eyes.solstory.domain.challenge.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserChallengeScheduler {
    private final UserChallengeService userChallengeService;

    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void checkAndRewardExpiredChallengesForAllUsers() {
        userChallengeService.checkAndRewardExpiredChallenges();
    }
}
