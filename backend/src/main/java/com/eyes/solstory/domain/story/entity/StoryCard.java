package com.eyes.solstory.domain.story.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "story_cards")
@SequenceGenerator(
	    name = "card_seq_generator",
	    sequenceName = "card_seq", // 오라클에 생성한 시퀀스 이름
	    allocationSize = 1  // 시퀀스 값을 하나씩 증가
	)
public class StoryCard {

	// 카드 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq_generator")
    @Column(name = "card_no", precision = 10)
    private int cardNo;

    // 카드 종류(인물/아이템/상황)
    @Column(name = "card_type", nullable = false, length = 50)
    private String cardType;

    // 카드 상세 키워드
    @Column(name = "card_keyword", nullable = false, length = 100)
    private String cardKeyword;

    // 카드를 얻기 위해 필요한 열쇠 수
    @Column(name = "keys_required", nullable = false, precision = 2)
    private int keysRequired;
}