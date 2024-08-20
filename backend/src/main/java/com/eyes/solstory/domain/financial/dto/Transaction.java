package com.eyes.solstory.domain.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
	
	private String transactionDate;
	//private String trasactionType; //2. 출금
	private String transactionAccountNo; // 입금된 사장님네 계좌번호
	private int transactionBalance; // 지출금액
	private String category; // 지출 카테고리
	private String transactionName; // 지출처 이름

	@Override
	public String toString() {
		return "Transaction [transactionDate=" + transactionDate + ", transactionAccountNo=" + transactionAccountNo
				+ ", transactionBalance=" + transactionBalance + ", category=" + category + ", transactionName="
				+ transactionName + "]";
	}
	

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionAccountNo() {
		return transactionAccountNo;
	}

	public void setTransactionAccountNo(String transactionAccountNo) {
		this.transactionAccountNo = transactionAccountNo;
	}

	public int getTransactionBalance() {
		return transactionBalance;
	}

	public void setTransactionBalance(int transactionBalance) {
		this.transactionBalance = transactionBalance;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

}
