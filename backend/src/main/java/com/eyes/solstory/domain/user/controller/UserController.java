package com.eyes.solstory.domain.user.controller;

import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.user.dto.OneWonVerificationRes;
import com.eyes.solstory.domain.user.dto.TransferOneWonRes;
import com.eyes.solstory.domain.user.dto.UserRes;
import com.eyes.solstory.domain.user.service.UserService;
import com.eyes.solstory.global.bank.dto.SavingsAccountRes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 계정 생성
    @PostMapping("/user/account")
    public ResponseEntity<UserRes> createUserAccount(@RequestParam("userId") String userId, @RequestParam("email") String email) {
        return userService.createUserAccount(userId, email);
    }

    // 1원 송금
    @PostMapping("/transfer/one_won")
    public ResponseEntity<String> transferOneWon(
            @RequestParam("accountNo") String accountNo,
            @RequestParam("userId") String userId) throws URISyntaxException {
        System.out.println(accountNo);
        System.out.println(userId);

        return userService.transferOneWon(accountNo, userId);
    }

    // 1원 검증
    @PostMapping("/verify/one_won")
    public ResponseEntity<OneWonVerificationRes> verifyOneWon(
            @RequestParam String transmissionDate,
            @RequestParam String transmissionTime,
            @RequestParam String accountNo,
            @RequestParam String authCode,
            @RequestParam String userId) {
        return userService.verifyOneWon(transmissionDate, transmissionTime, accountNo, authCode, userId);
    }

    // 적금 계좌 생성
    @PostMapping("/savings/account")
    public ResponseEntity<SavingsAccountRes> createSavingAccount(
            @RequestParam("accountTypeUniqueNo") String accountTypeUniqueNo,
            @RequestParam("withdrawalAccountNo") String withdrawalAccountNo,
            @RequestParam("depositBalance") long depositBalance,
            @RequestParam("userId") String userId,
            @RequestParam("targetAmount") int targetAmount) {
        return userService.createSavingAccount(accountTypeUniqueNo, withdrawalAccountNo, depositBalance, userId, targetAmount);
    }
}