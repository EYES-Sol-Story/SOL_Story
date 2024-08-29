package com.eyes.solstory.domain.userinfo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import com.eyes.solstory.domain.userinfo.repository.HobbyRepository;
import com.eyes.solstory.domain.userinfo.repository.InterestRepository;
import com.eyes.solstory.domain.userinfo.service.UserInfoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {

    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final HobbyRepository hobbyRepository;
    private final UserInfoService userInfoService;
    
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class.getSimpleName());
    
    // User 관련 API
    // 처음 정보 등록 
    @PostMapping("/saveInfo")
    public ResponseEntity<String> saveUserInfo(@RequestBody Map<String, Object> userInfo) {
    	logger.info("saveUserInfo()...{}", userInfo.toString());
    	userRepository.updateUserByMbti((String)userInfo.get("mbti"), (int)userInfo.get("userNo"));
        userInfoService.insertUserInfo(userInfo);
        return ResponseEntity.ok("success");
    }
    
    @GetMapping("/{userNo}")
    public ResponseEntity<User> findUserByUserNo(@PathVariable("userNo") int userNo){
    	logger.info("findUserByUserNo()...{}", userNo);
    	User user = userRepository.findUserByUserNo(userNo);
    	return ResponseEntity.ok(user);
    }
    
    @GetMapping("/interests/{userNo}")
    public ResponseEntity<List<String>> getInterestsByUserNo(@PathVariable("userNo") int userNo) {
    	logger.info("getInterest()...{}", userNo);
    	List<String> list = interestRepository.findAllInterestByUserNo(userNo);
    	if(list.isEmpty()) ResponseEntity.ok(list);
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/hobbies/{userNo}")
    public ResponseEntity<List<String>> getHobbiesByUserNo(@PathVariable("userNo") int userNo) {
    	logger.info("getHobby()...{}", userNo);
    	List<String> list = hobbyRepository.findAllHobbyByUserNo(userNo);
    	if(list.isEmpty()) ResponseEntity.ok(list);
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}