package com.eyes.solstory.domain.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@SequenceGenerator(
	    name = "challenge_seq_generator",
	    sequenceName = "challenge_seq", // 오라클에 생성한 시퀀스 이름
	    allocationSize = 1  // 시퀀스 값을 하나씩 증가
	)
public class Challenge {

	// 챌린지 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_seq_generator")
    @Column(name = "challenge_no", precision = 10)
    private int challengeNo;

    // 챌린지 이름
    @Column(name = "challenge_name", nullable = false, length = 255)
    private String challengeName;

    // 챌린지 설명
    @Column(name = "challenge_description", length = 255)
    private String challengeDescription;

    // 챌린지 과제 완료 시 획득할 포인트(1000point = 1key)
    @Column(name = "reward_points", nullable = false, precision = 5)
    private int rewardPoints;

    // 챌린지 유형(1:저축 , 2:지출)
    @Column(name = "challenge_type", nullable = false, precision = 1)
    private int challengeType;
    
}