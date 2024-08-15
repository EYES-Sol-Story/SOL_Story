package com.eyes.solstory.domain.tracsaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_categories")
public class TransactionCategory {

	// 거래 카테고리 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long categoryNo;

    // 거래 카테고리 이름
    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    public TransactionCategory() {}

    public TransactionCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getters and Setters
}