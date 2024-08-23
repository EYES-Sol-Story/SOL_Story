package com.eyes.solstory.domain.challenge.repository;

import com.eyes.solstory.domain.challenge.entity.UserChallenge;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Integer> {
    UserChallenge findByUserNo(int userNo);
}

