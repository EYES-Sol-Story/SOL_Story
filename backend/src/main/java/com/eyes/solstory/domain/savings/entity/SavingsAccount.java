package com.eyes.solstory.domain.savings.entity;

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
@Table(name = "savings_accounts")
public class SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_serial_no")
    private Long accountSerialNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Column(name = "account_no", nullable = false, length = 34)
    private String accountNo;

    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "savings_round")
    private Integer savingsRound;

    @Temporal(TemporalType.DATE)
    @Column(name = "last_deposit_date", nullable = false)
    private Date lastDepositDate;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "account_types", nullable = false)
    private Integer accountTypes;

    // 기본 생성자
    public SavingsAccount() {}

    // Getters and Setters

}