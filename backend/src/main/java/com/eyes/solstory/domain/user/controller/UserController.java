package com.eyes.solstory.domain.user.controller;

import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
   
	private UserService userService;
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class.getSimpleName());	

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
        System.out.println("userKey()> email: " + email );
    	return userService.searchUserkey(email);
    }
    
    
    
    
    
    ////////////gabin
    
    //회원가입 때 쓸 것. 유저의 정보를 테이블에 담은 후, user_no를 다시 받아서 그 값을 다음 페이지에 넘겨줄 것
    //회원가입해도 로그인해야하니까 
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
    	logger.info("signup()...{}", userDto.toString());
		int userNo = userService.saveUser(userDto);
		return ResponseEntity.ok().body(Map.of("user_no", userNo));
    }
    
    //이메일을 가지고 유저가 존재하는지 확인하는 용도
    @GetMapping("/getUserIdByEmail")
    public ResponseEntity<String> getUserIdByEmail(@RequestParam String email) {
        String user_id = userService.findUserIdByEmail(email);
        if (user_id != null) {
            return ResponseEntity.ok(user_id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    
    

    /**
     * 
     * @param loginRequest
     * @return
     * 
     * LoginRes (json 형태)
     * {	"loginResult"  : true,  // false
     * 		"loginUser" : {  //null
     * 						"userNo" 	: 1,
     * 						"userName"  : "지히"
     * 					  }
     * } 
     */
    //로그인페이지에서 쓸 것. userNo를 반환하거나 "noUser"를 반환.
    //로그인페이지에서 "로그인"클릭 시, noUser를 반환받게 된다면 메인화면 페이지로 넘어가지 않음.
    //로그인페이지에서 "로그인"클릭 시, user_no를 반환받게 된다면 그대로 메인화면 페이지에 넘겨줌.
    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginRequest) {
	    System.out.println("로그인컨트롤러에 들어옴");
	    System.out.println(loginRequest.toString());
	    boolean isAuthenticated = userService.authenticate(loginRequest.getUserId(), loginRequest.getPassword());
		if (isAuthenticated) {
		    int userNo = userService.getUserNo(loginRequest);
		    System.out.println("로그인에서 userNo : " + userNo);
		    return ResponseEntity.ok().body(Map.of("userNo", userNo));  // 성공 시 "userNo" 반환
		} else {
		    return ResponseEntity.ok().body(Map.of("userNo", null));  // 실패 시 null 반환
		    }
	 }
     
     //아이디 중복확인 - 유저 아이디가 존재하는지 확인. 회원가입페이지에서 쓸 것
     @GetMapping("/check-userid")
     public ResponseEntity<Boolean> checkUserid(@RequestParam("userid") String userId) {
         System.out.println("아이디 중복확인중");
         return ResponseEntity.ok(userRepository.existsByUserId(userId));
     }
     
     //이메일 중복확인 - 유저 이메일이 존재하는지 확인. 회원가입 때 쓸 것
     @GetMapping("/check-email")
     public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
         System.out.println("이메일검사중. email : " + email);
         return ResponseEntity.ok(userRepository.existsByEmail(email));
     }
     
     //비밀번호 변경 - 비밀번호 변경 페이지에서 쓸 것, 아이디를 가지고 해당 아이디를 가진 회원의 비밀번호를 바꾸기!
     @PostMapping("/change-password") 
     public int changePassword(@RequestParam("userId") String userId, @RequestParam("password") String password) {
         int result = userRepository.changePassword(userId, password);
         return result;
     }
    
}