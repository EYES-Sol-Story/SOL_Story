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
@Table(name = "story_steps")
public class StoryStep {

	// 스토리 단계 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_no", precision = 10)
    private int stepNo;

    // 스토리 단계 이름
    @Column(name = "step_name", nullable = false, length = 50)
    private String stepName;

    // 스토리 해금에 필요한 열쇠 수
    @Column(name = "keys_required", nullable = false, precision = 2)
    private int keysRequired;
}