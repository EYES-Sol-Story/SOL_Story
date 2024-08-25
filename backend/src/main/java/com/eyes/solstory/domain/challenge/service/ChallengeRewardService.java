package com.eyes.solstory.domain.challenge.service;

import com.eyes.solstory.domain.challenge.entity.ChallengeReward;
import com.eyes.solstory.domain.challenge.repository.ChallengeRewardRepository;
import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeRewardService {
    private ChallengeRewardRepository challengeRewardRepository;
    private UserRepository userRepository;


    //현재 수집한 열쇠 조회
    public int findChallengeKey(String userId) {
        User user = userRepository.findUserByUserId(userId);
        return challengeRewardRepository.
                findChallengeRewardByUserNo(user.getUserNo())
                .getKeys();
    }
    //챌린지 점수 조회
    public int calScore(String userId) {
        User user = userRepository.findUserByUserId(userId);
        int key = challengeRewardRepository.findChallengeRewardByUserNo(user.getUserNo()).getKeys();
        if(key >= 10) {
            return 70;
        } else if (10 > key && key >= 5) {
            return 60;
        } else if (5 > key && key >= 3) {
            return 50;
        } else {
            return 30;
        }
    }
}