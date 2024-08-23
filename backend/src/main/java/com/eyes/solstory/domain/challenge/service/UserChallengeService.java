package com.eyes.solstory.domain.challenge.service;

import com.eyes.solstory.domain.challenge.entity.UserChallenge;
import com.eyes.solstory.domain.challenge.repository.UserChallengeRepository;
import com.eyes.solstory.global.exception.UserChallengeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    public UserChallenge getUserChallengesStatus(int userNo) {
        UserChallenge userChallenge = userChallengeRepository.findByUser_UserNo(userNo);
        if(userChallenge == null) {
            throw new UserChallengeNotFoundException("해당하는 챌린지가 없습니다.");
        }
        // 오늘 날짜와 도전 과제의 할당 날짜 및 종료 날짜를 비교
        LocalDate today = LocalDate.now();
        LocalDate endDate = userChallenge.getAssignedDate().plusDays(userChallenge.getChallenge().getDays() - 1);

        if (!today.isBefore(userChallenge.getAssignedDate()) && !today.isAfter(endDate)) {

            //챌린지 달성여부 체크 로직 추가
            return userChallenge;
        } else {
            return null;
        }
    }
}

