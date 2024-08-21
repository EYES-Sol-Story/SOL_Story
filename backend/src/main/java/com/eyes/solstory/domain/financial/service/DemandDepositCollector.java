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
import com.eyes.solstory.domain.financial.dto.TransactionDTO;
import com.eyes.solstory.util.OpenApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 계좌 거래 내역 수집 및 처리 
 */
@Service
public class DemandDepositCollector {
	
	private static final Logger logger = LoggerFactory.getLogger(FinancialSummaryProcessor.class);	
	
	/**
	 * 해당 계좌의 입출금 거래 내역 불러오기
	 * 
	 * @param accountNo 조회할 계좌번호
	 * @param date 조회할 날짜
	 * @return 입출금 거래 내역
	 * @throws URISyntaxException
	 */
	public Map<String, List<TransactionDTO>> fetchTransactions(String accountNo, String date) throws URISyntaxException {
        Map<String, String> headerMap = OpenApiUtil.createHeaders(OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST);
        Map<String, Object> requestMap = OpenApiUtil.createTransactionHistoryRequestData(accountNo, date, "A", headerMap);

        ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.DEMAND_DEPOSIT_URL + OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST), requestMap);

        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode listNode = rootNode.path("REC").path("list");
            return parseTransactionList(listNode);
        } catch (Exception e) {
        	logger.error("계좌 거래 내역 추출 중 오류 발생");
            throw new RuntimeException("계좌 거래 내역 추출 중 오류 발생", e);
        }

    }

	
    /**
	 * 응답데이터를 parsing하여 지출내역/수익내역을 반환
	 * @param listNode
	 * @return
	 */
    private Map<String, List<TransactionDTO>> parseTransactionList(JsonNode listNode) {
    	Map<String, List<TransactionDTO>> transactionMap = new HashMap<>();
        List<TransactionDTO> spendingList = new ArrayList<>();
        List<TransactionDTO> incomeList = new ArrayList<>();
        
        if (listNode.isArray()) {
            for (JsonNode item : listNode) {
                String transactionAccountNo = item.path("transactionAccountNo").asText();
                int transactionBalance = item.path("transactionBalance").asInt();
                String transactionSummary = item.path("transactionSummary").asText();
                String transactionType = item.path("transactionType").asText(); //1입금 2출금
                TransactionDTO transaction = TransactionDTO.builder()
                        .transactionAccountNo(transactionAccountNo)
                        .transactionBalance(transactionBalance)
                        .transactionSummary(transactionSummary)
                        .build();

                if(transactionType.equals("1")) spendingList.add(transaction);
                else incomeList.add(transaction);
            }
        }
        
        transactionMap.put("spendingList", spendingList);
        transactionMap.put("incomeList", incomeList);
        
        return transactionMap;
    }
}
