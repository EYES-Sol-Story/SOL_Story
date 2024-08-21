package com.eyes.solstory.domain.financial.service;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.dto.Transaction;
import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;
import com.eyes.solstory.domain.financial.repository.SummaryRepository;
import com.eyes.solstory.util.OpenApiUtil;
import com.eyes.solstory.util.SpendingCategoryClassifier;

/**
 * 금융 요약 정보 생성 및 저장
 */
@Service
public class SummaryService {
	
	static final DateTimeFormatter DATE_FORMATTER= DateTimeFormatter.ofPattern("yyyyMMdd");
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private SummaryRepository summaryRepository;
    @Autowired
    private TransactionService transactionService;
	
	/**
	 * 자정마다 모든 활성화 된 계좌의 전날 지출/저축 내역을 받아 summary 
	 * @throws URISyntaxException
	 */
	public void fetchAndStoreFinancialData() throws URISyntaxException {
	    // LocalDate yesterday = LocalDate.now().minusDays(1);
	    String yesterday = LocalDate.now().format(OpenApiUtil.DATE_FORMATTER);
	    List<Object[]> userAccounts = userAccountService.findActiveAccounts();

	    // 계좌마다
	    for (Object[] userAccount : userAccounts) {
	    	int userNo = (int)userAccount[0];
        	String accountNo = (String)userAccount[1];
        	
        	// 거래 내역 받아오기
        	List<Transaction> transactions = transactionService.fetchTransactions(accountNo, yesterday);
        	// 거래 지점 조회 및 카테고리 분류
        	transactionService.setTransactionAccountNames(transactions);
        	
        	categorizeSpendingTransactions(userNo,  transactions);
	    }
	    
	}
	
	
	/**
	 * 카테고리별 지출금액 분류
	 * @param userNo 지출 사용자 일련번호
	 * @param transactions 어제 지출 내역(상호명 포함)
	 */
	private void categorizeSpendingTransactions(int userNo, List<Transaction> transactions) {
	    // 카테고리, 카테고리 지출 금액합
		Map<String, Integer> categoryTotals = new HashMap<>();

	    // 거래 내역별 카테고리 분류, 카테고리 별 금액 합산
	    for (Transaction transaction : transactions) {
	        String category = SpendingCategoryClassifier.classify(transaction.getTransactionAccountName());
	        int amount = transaction.getTransactionBalance();
	        
	        categoryTotals.put(category, categoryTotals.getOrDefault(category, 0) + amount);
	    }
	    
	    createSpendingSummaries(userNo, categoryTotals);

	}
	
	private void createSpendingSummaries(int userNo, Map<String, Integer> categoryTotals) {
	    // 카테고리별 요약 정보를 DailyFinancialSummary 객체로 변환
	    List<DailyFinancialSummary> summaries = new ArrayList<>();
	    for (Map.Entry<String, Integer> entry : categoryTotals.entrySet()) {
	        DailyFinancialSummary summary = DailyFinancialSummary.builder()
	        		.userNo(userNo)
	        		.financialDate(LocalDate.now())
	        		.financialType(2)
	        		.category(entry.getKey())
	        		.totalAmount(entry.getValue())
	        		.build();
	        summaries.add(summary);
	    }
	    
	    storeSummaries(summaries);
	}


	// 요약된 데이터를 데이터베이스에 저장하는 메서드
	private void storeSummaries(List<DailyFinancialSummary> summaries) {
        summaryRepository.saveAll(summaries);
    }
}