package com.eyes.solstory.domain.financial.entity;

import java.time.LocalDate;

import com.eyes.solstory.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "daily_financial_summary")
public class DailyFinancialSummary {

	// summary 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_no")
    private int summaryNo;

    // 사용자 일련번호
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 산출 날짜
    @Column(name = "summary_date", nullable = false)
    private LocalDate summaryDate;

    // 산출 유형(1: 저축 , 2:소비)
    @Enumerated(EnumType.STRING)
    @Column(name = "financial_type", nullable = false)
    private FinancialType financialType;

    // 지출 카테고리
    @Column(name = "category", length = 50)
    private String category;

    // 총액(저축/지출 카테고리별)
    @Column(name = "total_amount", nullable = false)
    private int totalAmount;
}