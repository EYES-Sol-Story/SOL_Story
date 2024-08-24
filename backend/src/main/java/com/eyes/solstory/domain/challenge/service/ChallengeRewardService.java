package com.eyes.solstory.domain.challenge.service;

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
}