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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_no")
    private Long goalNo;

    @ManyToOne
    @JoinColumn(name = "account_serial_no", nullable = false)
    private SavingsAccount relatedAccount; // SavingsAccount Relationship

    @ManyToOne
    @JoinColumn(name = "category_no", nullable = false)
    private SavingsGoalCategory category; // SavingsGoalCategory Relationship

    @Column(name = "custom_goal", length = 100)
    private String customGoal;

    @Column(name = "target_amount", nullable = false)
    private Long targetAmount;

    @Column(name = "current_amount", nullable = false)
    private Long currentAmount;

    @Column(name = "monthly_deposit_amount", nullable = false)
    private Long monthlyDepositAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

}
