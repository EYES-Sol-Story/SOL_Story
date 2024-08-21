package com.eyes.solstory.domain.financial.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySpendingSummaryDTO {
	private String category;
	private int totalAmount;
	private double percentageChange;
}
