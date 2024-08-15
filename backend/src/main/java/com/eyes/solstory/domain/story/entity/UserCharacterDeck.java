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
@Table(name = "user_character_deck")
public class UserCharacterDeck {

	// 사용자가 보유한 인물 카드 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_character_no")
    private Long userCharacterNo;
    
    // 사용자 객체
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 인물 카드 객체
    @ManyToOne
    @JoinColumn(name = "character_no", nullable = false)
    private CharacterCard character;

    // 인물 카드 획득일자
    @Temporal(TemporalType.DATE)
    @Column(name = "acquisition_date", nullable = false)
    private Date acquisitionDate;

    // 사용자가 획득한 인물 카드 중 보유 여부('Y' : 보유중,  'N' : 제거됨)
    @Column(name = "is_active", nullable = false, length = 5)
    private String isActive;
    
}