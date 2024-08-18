package com.eyes.solstory.domain.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "challenge_rewards")
public class ChallengeReward {

	// 사용자 번호 (사용자와 1대1 관계로 단순 번호만 필요)
    @Id
    @Column(name = "user_no")
    private int userNo;

    // 사용자가 획득한 총 열쇠 수
    @Column(name = "total_keys", nullable = false)
    private int totalKeys;

    // 열쇠로 변환되지 않은 포인트(1000 포인트 미만)
    @Column(name = "remaining_points", nullable = false)
    private int remainingPoints;
}