package com.eyes.solstory.constants;

public class OpenApiUrls {
	//// 입출금 계좌
	// 예금주 조회
	static final String INQUIRE_DEMAND_DEPOSIT_ACCOUNT_HOLDER_NAME 
	= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccountHolderName";
	// 계좌 잔액 조회
	static final String INQUIRE_DEMAND_DEPOSIT_ACCOUNT_BALANCE
	= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositAccountBalance";
	// 계좌 출금
	static final String UPDATE_DEMAND_DEPOSIT_ACCOUNT_WITHDRAWAL
	= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/updateDemandDepositAccountWithdrawal";
	// 계좌 입금
	static final String UPDATE_DEMAND_DEPOSIT_ACCOUNT_DEPOSIT
	= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/updateDemandDepositAccountDeposit";
	// 계좌 이체
	static final String UPDATE_DEMAND_DEPOSIT_ACCOUNT_TRANSFER
	= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/updateDemandDepositAccountTransfer";
	// 계좌 거래 내역 조회
	static final String INQUIRE_TRANSACTION_HISTORY_LIST
	= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireTransactionHistoryList";
	// 계좌 거래 내역 조회(단건)
	static final String INQUIRE_TRANSACTION_HISTORY
	= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireTransactionHistory";

	//// 적금 계좌
	// 적금 납입 회차 조회
		static final String INQUIRE_PAYMENT
		= "https//finopenapi.ssafy.io/ssafy/api/v1/edu/savings/inquirePayment";
}
