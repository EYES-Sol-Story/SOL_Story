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
@Table(name = "deposit_history")
public class DepositHistory {
	
	// 입금 내역 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposit_no")
    private Long depositNo;

    // 저축 계좌 객체
    @ManyToOne
    @JoinColumn(name = "account_serial_no", nullable = false)
    private SavingsAccount savingsAccount;

    // 저축 금액
    @Column(name = "deposit_amount", nullable = false)
    private Long depositAmount;

    // 입금 일자
    @Temporal(TemporalType.DATE)
    @Column(name = "deposit_date", nullable = false)
    private Date depositDate;

}