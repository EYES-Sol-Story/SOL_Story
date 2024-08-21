package com.eyes.solstory.domain.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
	
	private String transactionDate;
	private String transactionAccountNo; // 입금된 사장님네 계좌번호
	private int transactionBalance; // 지출금액
	private String category; // 지출 카테고리
	private String transactionSummary; // 지출내용

	@Override
	public String toString() {
		return "Transaction [transactionDate=" + transactionDate + ", transactionAccountNo=" + transactionAccountNo
				+ ", transactionBalance=" + transactionBalance + ", category=" + category + ", transactionName="
				+ transactionSummary + "]";
	}
	
}
