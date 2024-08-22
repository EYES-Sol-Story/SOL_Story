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
public class CategorySpendingSummaryDTO {
	private String category;
	private int totalAmount;
	private int avgAmount;
	private String ageGroup;
	private int totalAmountBefore;
	private int difference;
	private double percentChange;
}
