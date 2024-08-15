package com.eyes.solstory.domain.story.entity;

import java.util.Date;

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
@Table(name = "user_stories")
public class UserStory {

	// 사용자가 해금한 스토리 키워드 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_story_no")
    private Long userStoryNo;

    // 사용자 객체
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 선택된 스토리 키워드 객체
    @ManyToOne
    @JoinColumn(name = "keyword_no", nullable = false)
    private StoryKeyword keyword;

    // 스토리 해금일자
    @Temporal(TemporalType.DATE)
    @Column(name = "unlock_date", nullable = false)
    private Date unlockDate;
}