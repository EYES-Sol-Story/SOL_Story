package com.eyes.solstory.domain.savings.entity;

import java.util.Date;

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
@Table(name = "savings_goals")
public class SavingsGoal {

	// 저축 목표 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_no")
    private Long goalNo;

    // 저축 계좌 객체
    @ManyToOne
    @JoinColumn(name = "account_serial_no", nullable = false)
    private SavingsAccount relatedAccount; 

    // 저축 목표 카테고리 객체
    @ManyToOne
    @JoinColumn(name = "category_no", nullable = false)
    private SavingsGoalCategory category; 

    // 사용자 지정 목표
    @Column(name = "custom_goal", length = 100)
    private String customGoal;

    // 저축 목표 금액
    @Column(name = "target_amount", nullable = false)
    private Long targetAmount;

    // 현재 저축 금액
    @Column(name = "current_amount", nullable = false)
    private Long currentAmount;

    // 매달 저축 산정 금액
    @Column(name = "monthly_deposit_amount", nullable = false)
    private Long monthlyDepositAmount;

    // 시작일자
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    // 종료일자
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

}
