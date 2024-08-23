package com.eyes.solstory.domain.financial.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.dto.StoreSpendingSummaryDTO;
import com.eyes.solstory.domain.financial.dto.TransactionDTO;
import com.eyes.solstory.util.TransactionCategoryClassifier;

@Service
public class SpendingSummaryProcessor {
	
	@Autowired
	private DemandDepositCollector demandDepositCollector;
	
	/**
	 * // 특정 카테고리의 지출처별 가게별 방문 정보 반환
	 * @param arr arr[0]:user_key, arr[1]:account_no arr[2]:category
	 * @return 
	 * @throws URISyntaxException
	 */
	public List<StoreSpendingSummaryDTO> fetchTransactionDataForMonth(String[] arr) throws URISyntaxException {
		// 한달 간의 지출 내역을 받아옴
		List<TransactionDTO> transactions = demandDepositCollector.fetchTransactionsForMonth(arr);
		return processSpendingStoreByCategory(arr[2], transactions);
	}
	
	/*private String category; // 지출 카테고리
	private String storeName; // 지출처
	private int visitCount; //  한달간 지출처 방문 횟수
	private int totalAmount; // 한달간 지출처에서 소비한 금액
	 */
	
	/**
	 * 가게별 소비 정보 
	 * @param category
	 * @param transactions
	 * @return
	 */
	private List<StoreSpendingSummaryDTO> processSpendingStoreByCategory(String category, List<TransactionDTO> transactions) {
		List<StoreSpendingSummaryDTO> visitedStores = new ArrayList<>();
		// 지출처, 지출정보
		Map<String, StoreSpendingSummaryDTO> map = new HashMap<>();
		for(TransactionDTO transaction : transactions) {
			// 이 지출 내역이 카테고리에 포함 돼
			String storeName = transaction.getTransactionSummary();
			int amount = transaction.getTransactionBalance();
			if(TransactionCategoryClassifier.isCategory(storeName, category)) {
				if(!map.containsKey(storeName)) {
					map.put(storeName, new StoreSpendingSummaryDTO(storeName, 1, amount));
				}else {
					StoreSpendingSummaryDTO store = map.get(storeName);
					store.setVisitCount(store.getVisitCount()+1);
					store.setTotalAmount(store.getTotalAmount() + amount);
				}
			}
		}
		
		visitedStores.addAll(map.values());
		// 지출이 큰 순으로 정렬
		Collections.sort(visitedStores, (store1, store2) -> store2.getTotalAmount() - store1.getTotalAmount());
		
		return visitedStores;
	}
	
	/**
	 * 키워드별 소비 정리
	 * // 특정 카테고리의 가장 지출이 많은 키워드 반환
	 * @param arr arr[0]:user_key, arr[1]:account_no arr[2]:category
	 * @return 
	 * @throws URISyntaxException
	 */
	public String getKeywordWithCategoryForMonth(String[] arr) throws URISyntaxException {
		// 한달 간의 저축 내역을 받아옴
		List<TransactionDTO> transactions = demandDepositCollector.fetchTransactionsForMonth(arr);
		return processSpendingKeywordByCategory(arr[2], transactions);
	}
	
	/**
	 * 키워드별 소비 정보 
	 * @param category
	 * @param transactions
	 * @return
	 */
	private String processSpendingKeywordByCategory(String category, List<TransactionDTO> transactions) {
		List<StoreSpendingSummaryDTO> list = new ArrayList<>();
		// 지출처, 지출정보
		Map<String, StoreSpendingSummaryDTO> map = new HashMap<>();
		// 지출처, 지출정보
		for(TransactionDTO transaction : transactions) {
			String storeName = transaction.getTransactionSummary();
			int amount = transaction.getTransactionBalance();
			String keyword = TransactionCategoryClassifier.keyword(storeName, category);
			// 이 지출 내역이 카테고리에 포함 돼
			if(keyword != null) {
				if(!map.containsKey(keyword)) {
					map.put(keyword, StoreSpendingSummaryDTO.builder()
										.storeName(keyword) //키워드로 써먹음
										.totalAmount(amount)
										.build()); 
				}else {
					StoreSpendingSummaryDTO store = map.get(keyword);
					store.setTotalAmount(store.getTotalAmount()+amount);
				}
			}
		}
		
		list.addAll(map.values());
		// 지출이 큰 순으로 정렬
		Collections.sort(list, (store1, store2) -> store2.getTotalAmount() - store1.getTotalAmount());
		
		StoreSpendingSummaryDTO store = list.get(0);
		return store.getStoreName(); //키워드
	}
}
