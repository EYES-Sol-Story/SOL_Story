package com.eyes.solstory.domain.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSpendingSummaryDTO {
	//private String category; // 지출 카테고리
	//private String keyword;  // 지출 서브카테고리 (분류를 위한 키워드)
	private String storeName; // 지출처
	private int visitCount; //  한달간 지출처 방문 횟수
	private int totalAmount; // 한달간 지출처에서 소비한 금액
}
