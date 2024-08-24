package com.eyes.solstory.domain.user.controller;

import com.eyes.solstory.domain.user.dto.OneWonVerificationRes;
import com.eyes.solstory.domain.user.dto.TransferOneWonRes;
import com.eyes.solstory.domain.user.dto.UserRes;
import com.eyes.solstory.domain.user.service.UserService;
import com.eyes.solstory.global.bank.dto.SavingsAccountRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 계정 생성
    @PostMapping("/user/account")
    public ResponseEntity<UserRes> createUserAccount(@RequestParam String userId, @RequestParam String email) {
        return userService.createUserAccount(userId, email);
    }

    // 1원 송금
    @PostMapping("/transfer/one_won")
    public ResponseEntity<TransferOneWonRes> transferOneWon(
            @RequestParam("accountNo") String accountNo,
            @RequestParam("userId") String userId) {
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
            @RequestParam String transmissionDate,
            @RequestParam String transmissionTime,
            @RequestParam String accountTypeUniqueNo,
            @RequestParam String withdrawalAccountNo,
            @RequestParam long depositBalance,
            @RequestParam String userId,
            @RequestParam int targetAmount) {
        return userService.createSavingAccount(transmissionDate, transmissionTime, accountTypeUniqueNo, withdrawalAccountNo, depositBalance, userId, targetAmount);
    }
}