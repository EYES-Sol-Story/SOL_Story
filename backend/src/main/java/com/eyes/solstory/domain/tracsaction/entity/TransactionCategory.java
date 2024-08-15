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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long categoryNo;

    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    // 기본 생성자
    public TransactionCategory() {}

    // 매개변수를 받는 생성자
    public TransactionCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getters and Setters
}