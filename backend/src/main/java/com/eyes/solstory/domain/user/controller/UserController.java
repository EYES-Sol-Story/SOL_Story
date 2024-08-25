package com.eyes.solstory.domain.user.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.user.dto.UserDto;
import com.eyes.solstory.domain.user.dto.UserRes;
import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import com.eyes.solstory.domain.user.service.UserService;
import com.eyes.solstory.global.bank.dto.SavingsAccountRes;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
    private UserService userService;
	@Autowired
	private UserRepository userRepository;

	///////////////////////////** UserAccount로 가면 어떨지 가빈이가 api 연동 주소를 바꿔도 되고
    // 사용자 계정 생성
    @PostMapping("/user/account")
    public ResponseEntity<UserRes> createUserAccount(@RequestParam("userId") String userId, @RequestParam("email") String email) {
        return userService.createUserAccount(userId, email);
    }


    // 1원 송금
    @PostMapping("/transfer/one_won")
    public ResponseEntity<String> transferOneWon(
            @RequestParam("accountNo") String accountNo,
            @RequestParam("email") String email) throws URISyntaxException {
        return userService.transferOneWon(accountNo, email);
    }

    // 1원 검증
    @PostMapping("/verify/one_won")
    public ResponseEntity<String> verifyOneWon(
            @RequestParam("accountNo") String accountNo,
            @RequestParam("authCode") String authCode,
            @RequestParam("email") String email) throws URISyntaxException {
        return userService.verifyOneWon(accountNo, authCode, email);
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

    //userkey 조회
    @GetMapping("/userkey")
    public ResponseEntity<String> searchUserkey(@RequestParam("email") String email) {
        return userService.searchUserkey(email);
    }
    
    
    
    
    
    ////////////gabin
    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);
        return "회원가입 성공";
    }
    
    @GetMapping("/getUserIdByEmail")
    public ResponseEntity<String> getUserIdByEmail(@RequestParam String email) {
        String user_id = userService.findUserIdByEmail(email);
        if (user_id != null) {
            return ResponseEntity.ok(user_id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    
//    @PostMapping("/getUserId")
//    public ResponseEntity<String> getUserId(@RequestParam String user_id) {
//        String user_id = request.get("user_id");
//        Optional<User> user = UserRepository.findByEmail(email);
//
//        if (user.isPresent()) {
//            Map<String, String> response = new HashMap<>();
//            response.put("userId", user.get().getUserId());
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
    	System.out.println("들어옴");
    	System.out.println(loginRequest.toString());
        boolean isAuthenticated = userService.authenticate(loginRequest.getUserId(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok().body("{\"success\": true}");
        } else {
            return ResponseEntity.ok().body("{\"success\": false}");
        }
    }
    
    //아이디 중복확인 - 유저 아이디가 존재하는지 확인
    @GetMapping("/check-userid")
    public ResponseEntity<Boolean> checkUserid(@RequestParam("userid") String userId) {
        return ResponseEntity.ok(userRepository.existsByUserId(userId));
    }
    
}