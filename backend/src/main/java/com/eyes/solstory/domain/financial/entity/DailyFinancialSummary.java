package com.eyes.solstory.domain.financial.entity;

import java.time.LocalDate;

import com.eyes.solstory.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "summary_no", precision = 10)
    private int summaryNo;

    // 사용자 일련번호
    @Column(name = "user_no", nullable = false)
    private int userNo;

    // 저축/소비 해당 날짜
    @Column(name = "financial_date", nullable = false)
    private LocalDate financialDate;

    // 산출 유형(1: 저축 , 2:소비)
    @Column(name = "financial_type", precision = 1, nullable = false)
    private int financialType;

    // 지출 카테고리
    @Column(name = "category", length = 50)
    private String category;

    // 총액(저축/지출 카테고리별)
    @Column(name = "total_amount", precision = 1, nullable = false)
    private int totalAmount;
}