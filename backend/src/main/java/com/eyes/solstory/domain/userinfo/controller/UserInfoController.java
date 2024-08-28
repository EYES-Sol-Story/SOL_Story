package com.eyes.solstory.domain.userinfo.controller;

import java.util.List;
import java.util.Map;

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
    

    // User 관련 API
    // 처음 정보 등록 
    @PostMapping("/users")
    public ResponseEntity<String> updateUserMbti(@RequestBody Map<String, Object> userInfo) {
    	System.out.println(userInfo.toString());
    	userRepository.updateUserByMbti((String)userInfo.get("mbti"), (int)userInfo.get("userNo"));
        userInfoService.insertUserInfo(userInfo);
        return ResponseEntity.ok("success");
    }
    
    @GetMapping("/user/{userNo}")
    public ResponseEntity<User> findUserByUserNo(@PathVariable("userNo") int userNo){
    	System.out.println("사용자 정보 받아오기 userNo : " + userNo);
    	User user = userRepository.findUserByUserNo(userNo);
    	return ResponseEntity.ok(user);
    }
    
    @PostMapping("/users/{userNo}")
    public ResponseEntity<Interest> createInterest(@RequestBody Interest interest) {
    	Interest savedInterest = interestRepository.save(interest);
        return ResponseEntity.ok(savedInterest);
    }

    @GetMapping("/interests/{userNo}")
    public List<String> getInterest(@PathVariable("userNo") int userNo) {
    	System.out.println("사용자 관심사 받아오기 userNo : " + userNo);
    	return interestRepository.findAllInterestByUserNo(userNo);
    }

    @GetMapping("/interests")
    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    // Hobby 관련 API
    @PostMapping("/hobbies")
    public ResponseEntity<Hobby> createHobby(@RequestBody Hobby hobby) {
    	Hobby savedHobby = hobbyRepository.save(hobby);
        return ResponseEntity.ok(savedHobby);
    }

    @GetMapping("/hobbies/{userNo}")
    public List<String> getHobby(@PathVariable("userNo") int userNo) {
    	System.out.println("사용자 취미 받아오기 userNo : " + userNo);
    	return hobbyRepository.findAllHobbyByUserNo(userNo);
    }

    @GetMapping("/hobbies")
    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }

}