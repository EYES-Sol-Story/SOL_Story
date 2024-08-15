package com.eyes.solstory.domain.analysis.entity;

import java.util.Date;

import com.eyes.solstory.domain.savings.entity.SavingsAccount;
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
@Table(name = "spending_analysis")
public class SpendingAnalysis {

	// 소비 분석 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_no")
    private Long analysisNo;

    // 사용자 객체
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 저축 계좌 객체
    @ManyToOne
    @JoinColumn(name = "account_serial_no", nullable = false)
    private SavingsAccount savingsAccount;

    // 분석 날짜
    @Temporal(TemporalType.DATE)
    @Column(name = "analysis_date", nullable = false)
    private Date analysisDate;

    // 가장 큰 소비 카테고리
    @Column(name = "most_frequent_merchant", nullable = false, length = 100)
    private String mostFrequentMerchant;

    // 가장 큰 낭비 카테고리
    @Column(name = "highest_spending_category", nullable = false, length = 100)
    private String highestSpendingCategory;

    // 절약할 수 있는 사항
    @Column(name = "recommended_savings", nullable = false, length = 500)
    private String recommendedSavings;
}