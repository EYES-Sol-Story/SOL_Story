package com.eyes.solstory.domain.story.entity;

import java.time.LocalDate;

import com.eyes.solstory.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "user_stories")
@SequenceGenerator(
	    name = "user_story_seq_generator",
	    sequenceName = "user_story_seq", // 오라클에 생성한 시퀀스 이름
	    allocationSize = 1  // 시퀀스 값을 하나씩 증가
	)
public class UserStory {

	// 생성된 사용자 스토리 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_story_seq_generator")
    @Column(name = "user_story_no", precision = 10)
    private Long userStoryNo;
    
    // 사용자 객체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 스토리 단계 객체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_no", nullable = false)
    private StoryStep storyStep;

    // 스토리 내용
    @Lob
    @Column(name = "story_content", nullable = false)
    private String storyContent;

    // 스토리 해금일자
    @Column(name = "unlock_date", nullable = false)
    private LocalDate unlockDate;
}