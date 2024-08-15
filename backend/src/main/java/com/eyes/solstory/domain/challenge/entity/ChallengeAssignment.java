package com.eyes.solstory.domain.challenge.entity;

import java.util.Date;

import com.eyes.solstory.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "challenge_assignments")
public class ChallengeAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_no")
    private Long assignmentNo;

    @ManyToOne
    @JoinColumn(name = "challenge_no", nullable = false)
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "assigned_date", nullable = false)
    private Date assignedDate;

    @Column(name = "is_selected", nullable = false, length = 5)
    private String isSelected; // 'Y' 또는 'N'으로 선택 여부를 표시

    
    // Getters and Setters
}