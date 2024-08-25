package com.eyes.solstory.domain.challenge.repository;

import com.eyes.solstory.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
}
