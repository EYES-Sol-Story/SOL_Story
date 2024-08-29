package com.eyes.solstory.domain.challenge.controller;

import com.eyes.solstory.domain.challenge.service.ChallengeRewardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge")
@RequiredArgsConstructor
public class ChallengeRewardController {

    private ChallengeRewardService challengeRewardService;
    private static final Logger logger = LoggerFactory.getLogger(ChallengeRewardController.class.getSimpleName());

    @GetMapping("/key")
    public ResponseEntity<Integer> getChallengeKey(@RequestParam String userId) {
        logger.info("getChallengeKey()...userId:{}", userId);
        int keys = challengeRewardService.findChallengeKey(userId);
        logger.error("foundKeys : {}", keys);
        return ResponseEntity.ok(keys);
    }

    @GetMapping("/score")
    public ResponseEntity<Integer> getScore(@RequestParam String userId) {
        logger.info("getScore()...userId:{}", userId);
        int score = challengeRewardService.calScore(userId);
        logger.error("foundScore : {}", score);
        return ResponseEntity.ok(score);
    }
}