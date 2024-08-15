package com.eyes.solstory.domain.tracsaction.entity;

import java.util.Date;

import com.eyes.solstory.domain.savings.entity.SavingsAccount;

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
@Table(name = "transactions")
public class Transaction {

	// 계좌 거래 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_no")
    private Long transactionNo;

    // 저축 계좌 객체
    @ManyToOne
    @JoinColumn(name = "account_serial_no", nullable = false)
    private SavingsAccount savingsAccount;

    // 거래 날짜
    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    // 거래 금액
    @Column(name = "amount", nullable = false)
    private Double amount;

    // 거래 카테고리 객체
    @ManyToOne
    @JoinColumn(name = "category_no", nullable = false)
    private TransactionCategory category;

    // 거래 설명
    @Column(name = "description", length = 255)
    private String description;

}