package com.eyes.solstory.domain.financial.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eyes.solstory.constants.OpenApiUrls;
import com.eyes.solstory.domain.financial.dto.Transaction;
import com.eyes.solstory.util.OpenApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 계좌 거래 내역 수집 및 처리 
 */
@Service
public class TransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(SummaryService.class);	
	
	/**
	 * 해당 계좌의 입출금 거래 내역 불러오기
	 * 
	 * @param accountNo 조회할 계좌번호
	 * @param date 조회할 날짜
	 * @return 입출금 거래 내역
	 * @throws URISyntaxException
	 */
	public List<Transaction> fetchTransactions(String accountNo, String date) throws URISyntaxException {
        Map<String, String> headerMap = OpenApiUtil.createHeaders(OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST);
        Map<String, Object> requestMap = createTransactionHistoryRequestData(accountNo, date, headerMap);

        ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.DEMAND_DEPOSIT_URL + OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST), requestMap);

        ObjectMapper objectMapper = new ObjectMapper();
        
        List<Transaction> transactions = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode listNode = rootNode.path("REC").path("list");
            transactions = parseTransactionList(listNode);
        } catch (Exception e) {
        	logger.error("계좌 거래 내역 추출 중 오류 발생");
            throw new RuntimeException("계좌 거래 내역 추출 중 오류 발생", e);
        }

        return transactions;
    }

	
	/**
	 * 결제되어 입금된 계좌번호로 가게 상호명(userName) 받아서 저장
	 * 
	 * @param userNo 지출내역 검사 중인 사용자번호
	 * @param transactions 어제 지출 내역
	 * @throws URISyntaxException
	 */
    public void setTransactionAccountNames(List<Transaction> transactions) throws URISyntaxException {
        for (Transaction transaction : transactions) {
            Map<String, String> headerMap = OpenApiUtil.createHeaders(OpenApiUrls.INQUIRE_DEMAND_DEPOSIT_ACCOUNT_HOLDER_NAME);
            Map<String, Object> requestMap = createAccountHolderNameRequestData(transaction, headerMap);

            ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.DEMAND_DEPOSIT_URL + OpenApiUrls.INQUIRE_DEMAND_DEPOSIT_ACCOUNT_HOLDER_NAME), requestMap);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                String storeName = rootNode.path("REC").path("userName").asText();
                transaction.setTransactionAccountName(storeName);
            } catch (Exception e) {
            	logger.error("계좌 정보 응답에서 지출 상호명 추출 중 오류 발생");
	            throw new RuntimeException("계좌 정보 응답에서 지출 상호명 추출 중 오류 발생", e);
            }
        }
    }

    /**
	 * 입출금 통장의 어제 날짜 출금(지출) 기록 불러오기 위한 요청데이터 생성
	 * 
	 * @param accountNo 조회할 계좌번호
	 * @param yesterday 어제 날짜
	 * @param headerMap 요청 헤더
	 * @return requestMap
	 */
    private Map<String, Object> createTransactionHistoryRequestData(String accountNo, String date, Map<String, String> headerMap) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("Header", headerMap);
        requestMap.put("accountNo", accountNo);
        requestMap.put("startDate", date);
        requestMap.put("endDate", date);
        requestMap.put("transactionType", "D");
        requestMap.put("orderByType", "ASC");
        return requestMap;
    }

    
    /**
     * 사용자가 결제한 계좌 주인의 이름을 받아오기(상호명)
     * 
     * @param accountNo 조회할 계좌번호
     * @param headerMap 요청 헤더
     * @return requestMap
     */
    private Map<String, Object> createAccountHolderNameRequestData(Transaction transaction, Map<String, String> headerMap) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("Header", headerMap);
        requestMap.put("accountNo", transaction.getTransactionAccountNo());
        return requestMap;
    }

    /**
	 * 응답데이터 parsing 결제한 곳의 계좌번호와 금액만을 반환
	 * @param listNode
	 * @return
	 */
    private List<Transaction> parseTransactionList(JsonNode listNode) {
        List<Transaction> transactionList = new ArrayList<>();
        if (listNode.isArray()) {
            for (JsonNode item : listNode) {
                String transactionAccountNo = item.path("transactionAccountNo").asText();
                int transactionBalance = item.path("transactionBalance").asInt();
                Transaction transaction = Transaction.builder()
                        .transactionAccountNo(transactionAccountNo)
                        .transactionBalance(transactionBalance)
                        .build();
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }
}
