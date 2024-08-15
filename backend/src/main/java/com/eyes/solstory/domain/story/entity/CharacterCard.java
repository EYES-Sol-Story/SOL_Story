package com.eyes.solstory.domain.story.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "character_cards")
public class CharacterCard {

	// 인물 카드 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_no")
    private Long characterNo;

    // 인물 카드 종류
    @Column(name = "character_type", nullable = false, length = 50)
    private String characterType;

    // 인물 이름
    @Column(name = "character_name", nullable = false, length = 100)
    private String characterName;

    // 인물 카드를 얻기 위해 필요한 열쇠 수
    @Column(name = "keys_required", nullable = false)
    private Integer keysRequired;
}