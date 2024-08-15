package com.eyes.solstory.domain.story.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "story_keywords")
public class StoryKeyword {

	// 스토리 키워드 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_no")
    private Long keywordNo;

    // 스토리 카테고리 객체
    @ManyToOne
    @JoinColumn(name = "category_no", nullable = false)
    private StoryCategory category;

    // 스토리 키워드
    @Column(name = "keyword", nullable = false, length = 100)
    private String keyword;
}