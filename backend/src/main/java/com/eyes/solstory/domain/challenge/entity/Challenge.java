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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_no")
    private Long challengeNo;

    @Column(name = "challenge_name", nullable = false, length = 255)
    private String challengeName;

    @Column(name = "challenge_description", length = 255)
    private String challengeDescription;

    @Column(name = "reward_points", nullable = false)
    private Integer rewardPoints;

    @Column(name = "reward_eco_mileage", nullable = false)
    private Integer rewardEcoMileage;

    @Column(name = "challenge_type", nullable = false)
    private Integer challengeType;

    // Getters and Setters
    
}