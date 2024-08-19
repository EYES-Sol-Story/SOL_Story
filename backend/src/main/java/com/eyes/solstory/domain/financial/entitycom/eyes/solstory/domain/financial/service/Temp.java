package com.eyes.solstory.domain.financial.entitycom.eyes.solstory.domain.financial.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Temp {
	static final String API_KEY = "cb6cca464d504a29a809ced072ba5aec";
    static final String API_USER_ID = "chaehee13@naver.com";
    static final String USER_KEY = "04e988f2-d086-495a-aa2f-67b0e911782f";
    
    public static void main(String[] args) {
		getgoods();
		createAccount();
	}
	
	// 예금상품조회
	public static int getgoods() {
		RestTemplate restTemplate = new RestTemplate();
		 
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String sysdate = LocalDateTime.now().format(dateFormatter);
        String systime = LocalDateTime.now().format(timeFormatter);
        	
        // 요청에 사용할 데이터 설정
        Map<String, Object> requestMap = new HashMap<>();
        Map<String, String> Header = new HashMap<>();
        Header.put("apiName", "inquireDemandDepositList");
        Header.put("transmissionDate", sysdate);
        Header.put("transmissionTime", systime);
        Header.put("institutionCode", "00100"); //기관코드 고정
        Header.put("fintechAppNo", "001"); //핀테크 앱 일련번호 고정
        Header.put("apiServiceCode", "inquireDemandDepositList");
        Header.put("institutionTransactionUniqueNo", sysdate+systime+"000005");
        
        Header.put("apiKey", API_KEY);
        //Header.put("userKey", USER_KEY);
        
        requestMap.put("Header", Header);

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
            "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositList", 
            HttpMethod.POST, 
            request, 
            String.class
        );

        // 응답 출력
        System.out.println("Response: " + response.getBody());
		
		return 1;
	}
	
	// 계좌 생성 
	public static void createAccount() {
		
		RestTemplate restTemplate = new RestTemplate();
		 
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String sysdate = LocalDateTime.now().format(dateFormatter);
        String systime = LocalDateTime.now().format(timeFormatter);
        	
        // 요청에 사용할 데이터 설정
        Map<String, Object> requestMap = new HashMap<>();
        Map<String, String> Header = new HashMap<>();
        Header.put("apiName", "createDemandDepositAccount");
        Header.put("transmissionDate", sysdate);
        Header.put("transmissionTime", systime);
        Header.put("institutionCode", "00100"); //기관코드 고정
        Header.put("fintechAppNo", "001"); //핀테크 앱 일련번호 고정
        Header.put("apiServiceCode", "createDemandDepositAccount");
        Header.put("institutionTransactionUniqueNo", sysdate+systime+"000005");
        
        Header.put("apiKey", API_KEY);
        Header.put("userKey", USER_KEY);
        
        requestMap.put("Header", Header);
        requestMap.put("accountTypeUniqueNo", "001-1-f39cb699f9924c"); 

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
            "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/createDemandDepositAccount", 
            HttpMethod.POST, 
            request, 
            String.class
        );

        // 응답 출력
        System.out.println("Response: " + response.getBody());
	}
	
	

	/*
	 * public static void main(String[] args) {
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
        Header.put("apiName", "inquireTransactionHistory");
        Header.put("transmissionDate", sysdate);
        Header.put("transmissionTime", systime);
        Header.put("institutionCode", "00100"); //기관코드 고정
        Header.put("fintechAppNo", "001"); //핀테크 앱 일련번호 고정
        Header.put("apiServiceCode", "inquireTransactionHistory");
        Header.put("institutionTransactionUniqueNo", sysdate+systime+"000005");
        
        Header.put("apiKey", "cb6cca464d504a29a809ced072ba5aec");
        Header.put("userKey", USER_KEY);
        
        requestMap.put("Header", Header);
        requestMap.put("accountNo", "110324442879");
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
            "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireTransactionHistory", 
            HttpMethod.POST, 
            request, 
            String.class
        );

        // 응답 출력
        System.out.println("Response: " + response.getBody());
    }
	 */
}

