package com.eyes.solstory.domain.challenge.service;

import com.eyes.solstory.domain.challenge.ChallengeDataInitializer;
import com.eyes.solstory.domain.challenge.entity.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeDataInitializer challengeDataInitializer;

    private Random random = new Random();

    public List<Challenge> getRandomSavingChallenges(int count) {
        List<Challenge> savingChallenges = challengeDataInitializer.getSavingChallenges();
        Collections.shuffle(savingChallenges, random);
        return savingChallenges.subList(0, Math.min(count, savingChallenges.size()));
    }

    public List<Challenge> getRandomSpendingChallenges(int count) {
        List<Challenge> spendingChallenges = challengeDataInitializer.getSpendingChallenges();
        Collections.shuffle(spendingChallenges, random);
        return spendingChallenges.subList(0, Math.min(count, spendingChallenges.size()));
    }
}
