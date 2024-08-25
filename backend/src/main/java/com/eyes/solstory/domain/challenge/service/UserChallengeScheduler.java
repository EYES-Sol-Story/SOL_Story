package com.eyes.solstory.domain.challenge.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserChallengeScheduler {
    private final UserChallengeService userChallengeService;

    @Scheduled(cron = "30 43 0 * * ?")
    public void checkAndRewardExpiredChallengesForAllUsers() {
        userChallengeService.checkAndRewardExpiredChallenges();
    }
}
