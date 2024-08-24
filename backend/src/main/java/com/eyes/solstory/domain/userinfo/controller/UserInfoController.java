package com.eyes.solstory.domain.userinfo.controller;



import java.util.List;
import java.util.Optional;

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
    
    // update

    // User 관련 API
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    
    @PostMapping("/users/update/info")
    public ResponseEntity<User> updateUserInfo(@RequestBody User user) {
    	return ResponseEntity.ok(userInfoService.updateUserInfo(user));
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<User> getUser(@PathVariable("userNo") int userNo) {
        Optional<User> user = userRepository.findUserByUserNo(userNo);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Interest 관련 API
    @PostMapping("/interests")
    public ResponseEntity<Interest> createInterest(@RequestBody Interest interest) {
        Interest savedInterest = interestRepository.save(interest);
        return ResponseEntity.ok(savedInterest);
    }

    @GetMapping("/interests/{userNo}")
    public ResponseEntity<Interest> getInterest(@PathVariable("userNo") int userNo) {
        Optional<Interest> interest = interestRepository.findByUserNo(userNo);
        return interest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    @GetMapping("/hobbies/{id}")
    public ResponseEntity<Hobby> getHobby(@PathVariable("userNo") int userNo) {
        Optional<Hobby> hobby = hobbyRepository.findByUserNo(userNo);
        return hobby.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/hobbies")
    public List<Hobby> getAllHobbies() {
        return hobbyRepository.findAll();
    }
}