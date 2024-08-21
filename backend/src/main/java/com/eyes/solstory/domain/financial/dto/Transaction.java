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
public class Transaction {
	
	private String transactionDate;
	//private String trasactionType; //2. 출금
	private String transactionAccountNo; // 입금된 사장님네 계좌번호
	private int transactionBalance; // 지출금액
	private String category; // 지출 카테고리
	private String transactionAccountName; // 지출처 이름

	@Override
	public String toString() {
		return "Transaction [transactionDate=" + transactionDate + ", transactionAccountNo=" + transactionAccountNo
				+ ", transactionBalance=" + transactionBalance + ", category=" + category + ", transactionName="
				+ transactionAccountName + "]";
	}
	
}
