package com.eyes.solstory.domain.userinfo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import com.eyes.solstory.domain.userinfo.entity.Hobby;
import com.eyes.solstory.domain.userinfo.entity.Interest;
import com.eyes.solstory.domain.userinfo.repository.HobbyRepository;
import com.eyes.solstory.domain.userinfo.repository.InterestRepository;
import com.eyes.solstory.domain.userinfo.service.UserInfoService;

@RestController
@RequestMapping("/api")
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
    @PostMapping("/users")
    public ResponseEntity<String> updateUserMbti(@RequestBody Map<String, Object> userInfo) {
        logger.info("updateUserMbti()...userInfo:{}", userInfo.toString());
    	userRepository.updateUserByMbti((String)userInfo.get("mbti"), (int)userInfo.get("userNo"));
        userInfoService.insertUserInfo(userInfo);
        return ResponseEntity.ok("success");
    }
    
    @GetMapping("/user/{userNo}")
    public ResponseEntity<User> findUserByUserNo(@PathVariable("userNo") int userNo){
        logger.info("findUserByUserNo()...userNo:{}", userNo);
    	User user = userRepository.findUserByUserNo(userNo);
    	return ResponseEntity.ok(user);
    }
    
    @PostMapping("/users/{userNo}")
    public ResponseEntity<Interest> createInterest(@RequestBody Interest interest) {
        logger.info("createInterest()...interest:{}", interest.toString());
    	Interest savedInterest = interestRepository.save(interest);
        return ResponseEntity.ok(savedInterest);
    }

    @GetMapping("/interests/{userNo}")
    public List<String> getInterest(@PathVariable("userNo") int userNo) {
        logger.info("getInterest()...userNo:{}", userNo);
    	return interestRepository.findAllInterestByUserNo(userNo);
    }

    @GetMapping("/interests")
    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    // Hobby 관련 API
    @PostMapping("/hobbies")
    public ResponseEntity<Hobby> createHobby(@RequestBody Hobby hobby) {
        logger.info("createHobby()...hobby:{}", hobby.toString());
    	Hobby savedHobby = hobbyRepository.save(hobby);
        return ResponseEntity.ok(savedHobby);
    }

    @GetMapping("/hobbies/{userNo}")
    public List<String> getHobby(@PathVariable("userNo") int userNo) {
        logger.info("getHobby()...userNo:{}", userNo);
    	return hobbyRepository.findAllHobbyByUserNo(userNo);
    }

    @GetMapping("/hobbies")
    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }

}