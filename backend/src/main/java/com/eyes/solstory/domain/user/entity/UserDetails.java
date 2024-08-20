package com.eyes.solstory.domain.user.entity;

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
@Table(name = "user_details")
public class UserDetails {	

	// 사용자 정보 일련번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_no", precision = 10)
	private int detailNo;
	
	// 사용자 일련번호
	@ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

	// 사용자 정보 타입 (1: MBTI, 2:취미, 3:관심사)
    @Column(name = "attribute_type", nullable = false, precision = 1)
    private int attributeType;

    // 사용자 정보 (MBTI타입, 취미/관심사 카테고리)
    @Column(name = "attribute_value", nullable = false, length = 50)
    private String attributeValue;

}