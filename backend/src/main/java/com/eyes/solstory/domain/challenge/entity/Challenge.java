package com.eyes.solstory.domain.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenges")
public class Challenge {

	// 챌린지 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_no")
    private int challengeNo;

    // 챌린지 이름
    @Column(name = "challenge_name", nullable = false, length = 255)
    private String challengeName;

    // 챌린지 설명
    @Column(name = "challenge_description", length = 255)
    private String challengeDescription;

    // 챌린지 과제 완료 시 획득할 포인트(1000point = 1key)
    @Column(name = "reward_points", nullable = false)
    private int rewardPoints;

    // 챌린지 유형(1:저축 , 2:지출)
    @Column(name = "challenge_type", nullable = false)
    private int challengeType;
    
}