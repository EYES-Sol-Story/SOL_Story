package com.eyes.solstory.domain.challenge.controller;

import com.eyes.solstory.domain.challenge.service.ChallengeRewardService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/key")
    public ResponseEntity<Integer> getChallengeKey(@RequestParam String userId) {
        int keys = challengeRewardService.findChallengeKey(userId);
        return ResponseEntity.ok(keys);
    }
}
