package com.eyes.solstory.domain.challenge.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.challenge.entity.Challenge;
import com.eyes.solstory.domain.challenge.entity.UserChallenge;
import com.eyes.solstory.domain.challenge.service.ChallengeService;
import com.eyes.solstory.domain.challenge.service.UserChallengeService;
import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import com.eyes.solstory.global.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/challenge")
@AllArgsConstructor
public class ChallengeController {

    private ChallengeService challengeService;
    private UserChallengeService userChallengeService;
    private UserRepository userRepository;

    @GetMapping("/list")
    public ResponseEntity<List<UserChallenge>> getChallengeList(@RequestParam("email") String email) {
        System.out.println(email);
        User user = userRepository.findUserByEmail(email);
        System.out.println(user);
        //저축 4개 지출 6개의 챌린지 반환
        List<Challenge> savingChallenges = challengeService.getRandomSavingChallenges(4);
        List<Challenge> spendingChallenges = challengeService.getRandomSpendingChallenges(6);

        List<Challenge> allChallenges = new ArrayList<>();
        allChallenges.addAll(savingChallenges);
        allChallenges.addAll(spendingChallenges);

        List<UserChallenge> userChallenges = challengeService.assignChallengesToUser(user, allChallenges);
        return ResponseEntity.ok(userChallenges);
    }

    //챌린지 조회
    @GetMapping("/status")
    public ResponseEntity<UserChallenge> getChallengeStatus(@RequestParam("userNo") int userNo,
            @RequestParam("startDate") String startDate) {
        LocalDate start = LocalDate.parse(startDate);
        UserChallenge userChallenge = userChallengeService.getUserChallengesStatus(userNo);
        return ResponseEntity.ok(userChallenge);
    }
}