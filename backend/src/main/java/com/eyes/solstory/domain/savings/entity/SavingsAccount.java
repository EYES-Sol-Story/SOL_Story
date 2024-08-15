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

	// 저축 계좌 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_serial_no")
    private Long accountSerialNo;

    // 사용자 객체
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 저축 계좌번호
    @Column(name = "account_no", nullable = false, length = 34)
    private String accountNo;

    // 계좌 별명
    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    // 계좌 잔액
    @Column(name = "balance", nullable = false)
    private Long balance;

    // 생성 일자
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    // 저축 회차
    @Column(name = "savings_round")
    private Integer savingsRound;

    // 최근 입금 일자
    @Temporal(TemporalType.DATE)
    @Column(name = "last_deposit_date", nullable = false)
    private Date lastDepositDate;

    // 계좌 상태(0 : 종료, 1:활성화)
    @Column(name = "status", nullable = false)
    private Integer status;

    // 계좌 유형(1: 저축 계좌, 2: 주거래 계좌)
    @Column(name = "account_types", nullable = false)
    private Integer accountTypes;

    public SavingsAccount() {}

    // Getters and Setters

}