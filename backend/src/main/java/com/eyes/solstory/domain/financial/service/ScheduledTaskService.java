package com.eyes.solstory.domain.financial.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;
import com.eyes.solstory.domain.financial.repository.SummaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ScheduledTaskService {
	
	@Autowired
    private SummaryRepository summaryRepository;
	
	// 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void fetchAndSaveFinancialData() {
    	// 어제 날짜 가져오기
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // 신한은행 API를 호출하여 데이터를 가져옴
        List<DailyFinancialSummary> summaries = fetchTransactionDataFrom(yesterday);

        // 데이터를 데이터베이스에 저장
        for (DailyFinancialSummary summary : summaries) {
            summaryRepository.save(summary);
        }
    }

    // 
    static final String API_KEY = "cb6cca464d504a29a809ced072ba5aec";
    static final String API_USER_ID = "chaehee13@naver.com";
    static final String USER_KEY = "04e988f2-d086-495a-aa2f-67b0e911782f";

    /**
     * 신한은행 API를 통해 전날 거래 내역을 받아옴
     * 
     * @param date 어제 날짜
     * @return
     */
    private List<DailyFinancialSummary> fetchTransactionDataFrom(LocalDate date) {
    	
    	
    	
    	
        return List.of(); 
    }
    
    public static void main(String[] args) {
        // RestTemplate 인스턴스 생성
        RestTemplate restTemplate = new RestTemplate();
        
        // 요청에 사용할 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String sysdate = LocalDateTime.now().format(dateFormatter);
        String systime = LocalDateTime.now().format(timeFormatter);
        	
        // 요청에 사용할 데이터 설정
        Map<String, Object> requestMap = new HashMap<>();
        Map<String, String> Header = new HashMap<>();
        Header.put("apiName", "inquireTransactionHistoryList");
        Header.put("transmissionDate", sysdate);
        Header.put("transmissionTime", systime);
        Header.put("institutionCode", "00100"); //기관코드 고정
        Header.put("fintechAppNo", "001"); //핀테크 앱 일련번호 고정
        Header.put("apiServiceCode", "inquireTransactionHistoryList");
        Header.put("institutionTransactionUniqueNo", sysdate+systime+"000005");
        
        Header.put("apiKey", API_KEY);
        Header.put("userKey", USER_KEY);
        
        requestMap.put("Header", Header);
        requestMap.put("accountNo", "0017592758195132");
        requestMap.put("startDate", "20240816");
        requestMap.put("endDate", "20240816");
        requestMap.put("transactionType", "D"); //출금
        requestMap.put("orderByType", "ASC"); //오름차순

        // Map을 JSON 형식으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(requestMap);
        } catch (Exception e) {
            throw new RuntimeException("JSON 변환 중 오류 발생", e);
        }
        
        // 요청 엔티티 생성
        HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);
        
        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
        	//GET_API_KEY_URL,
            "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireTransactionHistoryList", 
            HttpMethod.POST, 
            request, 
            String.class
        );

        // 응답 출력
        System.out.println("Response: " + response.getBody());
    }

}
