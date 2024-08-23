package com.eyes.solstory.domain.challenge.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.challenge.ChallengeDataInitializer;
import com.eyes.solstory.domain.challenge.entity.Challenge;
import com.eyes.solstory.domain.challenge.entity.UserChallenge;
import com.eyes.solstory.domain.challenge.repository.UserChallengeRepository;
import com.eyes.solstory.domain.user.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChallengeService {
    private ChallengeDataInitializer challengeDataInitializer;
    private final UserChallengeRepository userChallengeRepository;

    public List<Challenge> getRandomSavingChallenges(int count) {
    	Random random = new Random();
        List<Challenge> savingChallenges = challengeDataInitializer.getSavingChallenges();
        Collections.shuffle(savingChallenges, random);
        return savingChallenges.subList(0, Math.min(count, savingChallenges.size()));
    }

    public List<Challenge> getRandomSpendingChallenges(int count) {
    	Random random = new Random();
        List<Challenge> spendingChallenges = challengeDataInitializer.getSpendingChallenges();
        Collections.shuffle(spendingChallenges, random);
        return spendingChallenges.subList(0, Math.min(count, spendingChallenges.size()));
    }

    public List<UserChallenge> assignChallengesToUser(User user, List<Challenge> challenges) {
        List<UserChallenge> userChallenges = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Challenge challenge : challenges) {
            UserChallenge userChallenge = UserChallenge.builder()
                    .user(user)
                    .challenge(challenge)
                    .assignedDate(today)
                    .build();

            userChallengeRepository.save(userChallenge);
            userChallenges.add(userChallenge);
        }

        return userChallenges;
    }
}
