package com.eyes.solstory.domain.story.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "story_categories")
public class StoryCategory {

	// 스토리 카테고리 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long categoryNo;

    // 스토리 카테고리 이름
    @Column(name = "category_name", nullable = false, length = 50)
    private String categoryName;

    // 스토리 해금에 필요한 열쇠 수
    @Column(name = "keys_required", nullable = false)
    private Integer keysRequired;
}