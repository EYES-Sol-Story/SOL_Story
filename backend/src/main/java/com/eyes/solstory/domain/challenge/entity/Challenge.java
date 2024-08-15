package com.eyes.solstory.domain.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "challenges")
public class Challenge {

	// 도전과제 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_no")
    private Long challengeNo;

    // 도전과제 이름
    @Column(name = "challenge_name", nullable = false, length = 255)
    private String challengeName;

    // 도전과제 설명
    @Column(name = "challenge_description", length = 255)
    private String challengeDescription;

    // 도전과제 완료 시 획득할 포인트(1000point = 1key)
    @Column(name = "reward_points", nullable = false)
    private Integer rewardPoints;

    // 도전과제 유형(0:일반 미션, 1: ECO 미션)
    @Column(name = "challenge_type", nullable = false)
    private Integer challengeType;
    
    // 기본 생성자
    public Challenge() {}

    // Getters and Setters
    
    public Long getChallengeNo() {
        return challengeNo;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    } 
    
    
    
}