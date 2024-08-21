package com.eyes.solstory.domain.challenge.entity;

import java.time.LocalDate;

import com.eyes.solstory.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "user_challenges")
public class UserChallenge {

	// 제시된 도전과제 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_no", precision = 10)
    private int assignmentNo;

    // 사용자 객체
    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // 도전과제 일련번호
    @ManyToOne
    @JoinColumn(name = "challenge_no", nullable = false)
    private Challenge challenge;

    // 도전과제가 사용자에게 할당된 날짜
    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;

    // 사용자가 해당 도전과제를 선택했는지 여부 ('Y'/ 'N')
    @Column(name = "is_selected", nullable = false, length = 5)
    private String isSelected;

    // 도전과제 완료일자
    @Column(name = "complete_date")
    private LocalDate completeDate;
}