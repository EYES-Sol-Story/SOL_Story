package com.eyes.solstory.domain.challenge.controller;

import com.eyes.solstory.domain.challenge.entity.Challenge;
import com.eyes.solstory.domain.challenge.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping("/list")
    public List<Challenge> getRandomChallenges() {
        //저축 4개 지출 6개의 챌린지 반환
        List<Challenge> savingChallenges = challengeService.getRandomSavingChallenges(4);
        List<Challenge> spendingChallenges = challengeService.getRandomSpendingChallenges(6);

        savingChallenges.addAll(spendingChallenges);
        return savingChallenges;
    }
}
