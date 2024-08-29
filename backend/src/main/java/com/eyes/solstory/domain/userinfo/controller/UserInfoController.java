package com.eyes.solstory.domain.userinfo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import com.eyes.solstory.domain.userinfo.entity.UserHobby;
import com.eyes.solstory.domain.userinfo.entity.UserInterest;
import com.eyes.solstory.domain.userinfo.repository.HobbyRepository;
import com.eyes.solstory.domain.userinfo.repository.InterestRepository;
import com.eyes.solstory.domain.userinfo.service.UserInfoService;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private UserInfoService userInfoService;
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class.getSimpleName());

    // User 관련 API
    // 처음 정보 등록 
    @PostMapping("/saveInfo")
    public ResponseEntity<String> updateUserMbti(@RequestBody Map<String, Object> userInfo) {
        logger.info("updateUserMbti()...userInfo:{}", userInfo.toString());
    	userRepository.updateUserByMbti((String)userInfo.get("mbti"), (int)userInfo.get("userNo"));
        userInfoService.insertUserInfo(userInfo);
        return ResponseEntity.ok("success");
    }
    
    @GetMapping(value="/mbti", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findMbtiByUserNo(@RequestParam("userNo") int userNo){
    	logger.info("findMbtiByUserNo()...{}", userNo);
    	User user = userRepository.findUserByUserNo(userNo);
    	return user.getMbti();
    }
    
    @GetMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUserByUserNo(@RequestParam("userNo") int userNo){
    	logger.info("findUserByUserNo()...{}", userNo);
    	User user = userRepository.findUserByUserNo(userNo);
    	logger.info("user: {}", user);
    	return ResponseEntity.ok(user);
    }
    
    @PostMapping("/users/{userNo}")
    public ResponseEntity<UserInterest> createInterest(@RequestBody UserInterest interest) {
        logger.info("createInterest()...interest:{}", interest.toString());
    	UserInterest savedInterest = interestRepository.save(interest);
        return ResponseEntity.ok(savedInterest);
    }

    @GetMapping(value = "/interests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getInterestsByUserNo(@RequestParam("userNo") int userNo) {
    	logger.info("getInterest()...{}", userNo);
    	List<String> list = interestRepository.getInterestByUserNo(userNo);
    	if(list.isEmpty()) ResponseEntity.ok(list);
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/interests")
    public List<UserInterest> getAllInterests() {
        return interestRepository.findAll();
    }

    // Hobby 관련 API
    @PostMapping("/hobbies")
    public ResponseEntity<UserHobby> createHobby(@RequestBody UserHobby hobby) {
        logger.info("createHobby()...hobby:{}", hobby.toString());
    	UserHobby savedHobby = hobbyRepository.save(hobby);
        return ResponseEntity.ok(savedHobby);
    }

    @GetMapping(value = "/hobbies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getHobbiesByUserNo(@RequestParam("userNo") int userNo) {
    	logger.info("getHobby()...{}", userNo);
    	List<String> list = hobbyRepository.getHobbyUserNo(userNo);
    	if(list.isEmpty()) ResponseEntity.ok(list);
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/hobbies")
    public List<UserHobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }

}