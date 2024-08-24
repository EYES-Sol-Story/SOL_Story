package com.eyes.solstory.domain.challenge.repository;

import com.eyes.solstory.domain.challenge.entity.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Integer> {
    UserChallenge findByUser_UserNo(int userNo);
}

