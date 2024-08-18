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
@Table(name = "user_card_deck")
public class UserCardDeck {

	// 사용자가 보유한 카드 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_card_no")
    private Long userCardNo;

    // 카드 객체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_no", nullable = false)
    private StoryCard storyCard;

    // 카드가 사용된 스토리 객체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_story_no", nullable = true)
    private UserStory userStory;

    // 사용자 일련번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 인물 카드 획득일자
    @Column(name = "acquisition_date", nullable = false)
    private LocalDate acquisitionDate;

    // 사용자가 획득한 인물 카드 중 보유 여부('Y' : 보유중,  'N' : 사용완료)
    @Column(name = "is_active", nullable = false, length = 5)
    private String isActive;
    
}