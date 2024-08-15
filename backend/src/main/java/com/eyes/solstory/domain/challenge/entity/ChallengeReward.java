package com.eyes.solstory.domain.challenge.entity;

import com.eyes.solstory.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "challenge_rewards")
public class ChallengeReward {

	// 리워드 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_no")
    private Long rewardNo;

    // 사용자 객체
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 도전과제 객체
    @ManyToOne
    @JoinColumn(name = "challenge_no", nullable = false)
    private Challenge challenge;

    // 도전과제 완료시 획득한 포인트
    @Column(name = "reward_points", nullable = false)
    private Integer rewardPoints;

    // 사용자가 획득한 총 열쇠 수
    @Column(name = "total_keys", nullable = false)
    private Integer totalKeys;

    // 열쇠로 변환되지 않은 포인트(1000 포인트 미만)
    @Column(name = "remaining_points", nullable = false)
    private Integer remainingPoints;
    
    // Getters and Setters
    
    public Long getChallengeNo() {
        return challenge.getChallengeNo();
    }

    public Integer getRewardPoints() {
        return challenge.getRewardPoints();
    }
}