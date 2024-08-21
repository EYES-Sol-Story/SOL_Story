package com.eyes.solstory.domain.financial.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eyes.solstory.constants.OpenApiUrls;
import com.eyes.solstory.util.OpenApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 저축 계좌 거래 내역 수집 및 처리
 */
@Service
public class SavingsCollector {

	private static final Logger logger = LoggerFactory.getLogger(FinancialSummaryProcessor.class);	

	/**
	 * 해당 계좌의 저축 내역 불러오기
	 * 
	 * @param accountNo 조회할 계좌번호
	 * @param date 조회할 날짜
	 * @return 입출금 거래 내역
	 * @throws URISyntaxException
	 */
	public int fetchSavings(String accountNo, String date) throws URISyntaxException {
        Map<String, String> headerMap = OpenApiUtil.createHeaders(OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST);
        Map<String, Object> requestMap = OpenApiUtil.createTransactionHistoryRequestData(accountNo, date, "M", headerMap);

        ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.DEMAND_DEPOSIT_URL + OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST), requestMap);

        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode listNode = rootNode.path("REC").path("list");
            return parseTransactionList(listNode);
        } catch (Exception e) {
        	logger.error("저축 내역 추출 중 오류 발생");
            throw new RuntimeException("저축 내역 추출 중 오류 발생", e);
        }

    }
	
	 /**
	 * 응답데이터 parsing 
	 * @param listNode
	 * @return
	 */
    private int parseTransactionList(JsonNode listNode) {
        int savings = 0;
        if (listNode.isArray()) {
            for (JsonNode item : listNode) {
            	savings += item.path("transactionBalance").asInt();
            }
        }
        return savings;
    }

}
