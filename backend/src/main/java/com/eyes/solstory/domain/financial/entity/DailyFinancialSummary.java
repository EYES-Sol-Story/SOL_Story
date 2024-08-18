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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Column(name = "summary_date", nullable = false)
    private LocalDate summaryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "financial_type", nullable = false)
    private FinancialType financialType;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;
}