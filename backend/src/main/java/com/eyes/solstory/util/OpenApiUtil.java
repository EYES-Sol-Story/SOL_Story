package com.eyes.solstory.util;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public class OpenApiUtil {
	
	public static final String API_KEY = "cb6cca464d504a29a809ced072ba5aec";
	public static final String USER_KEY = "04e988f2-d086-495a-aa2f-67b0e911782f";
	
	public static final DateTimeFormatter DATE_FORMATTER= DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    
    /**
     * OpenApi 사용 시 Header 생성 
     * 날짜와 랜덤을 메서드 내에서 생성 
     * 
     * @param apiName Open Api Url 의 엔드포인트
     * @return header
     */
	public static Map<String, String> createHeaders(String apiName) {
		Random random = new Random();
		String sysdate = LocalDate.now().format(DATE_FORMATTER);
        return createHeaders(apiName, sysdate, random);
    }
	
	/**
     * OpenApi 사용 시 Header 생성
     * 날짜, 랜덤을 모두 파라미터로 받아옴 (스케줄링 시 같은 값을 계속 쓸 것이기 때문)
     * 
     * @param apiName Open Api Url 의 엔드포인트
     * @return header
     */
	public static Map<String, String> createHeaders(String apiName, String sysdate, Random random) {
        String systime = LocalDateTime.now().format(TIME_FORMATTER);
		
		Map<String, String> headerMap = new HashMap<>();
        headerMap.put("apiName", apiName);
        headerMap.put("transmissionDate", sysdate);
        headerMap.put("transmissionTime", systime);
        headerMap.put("institutionCode", "00100");
        headerMap.put("fintechAppNo", "001");
        headerMap.put("apiServiceCode", apiName);
        headerMap.put("institutionTransactionUniqueNo", sysdate + systime + String.format("%06d", random.nextInt(1000000)));
        headerMap.put("apiKey", API_KEY);
        headerMap.put("userKey", USER_KEY);
        return headerMap;
    }
	
	/**
	 * Open Api 호출 (단건)
	 * 
	 * @param uri 호출할 URI
	 * @param request 데이터
	 * @return String 타입의 응답entity 반환
	 */
	public static ResponseEntity<String> callApi(URI uri, Map<String, Object> requestMap) {
		String jsonRequest = convertToJson(requestMap);
		HttpEntity<String> request = createHttpEntity(jsonRequest);
		RestTemplate restTemplate = new RestTemplate();
	    return restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
	}
	
	/**
	 * 맵 형태의 요청 데이터를 Json 형태의 문자열로 변환
	 * 
	 * @param requestMap 
	 * @return 
	 */
	private static String convertToJson(Map<String, Object> requestMap) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        return objectMapper.writeValueAsString(requestMap);
	    } catch (Exception e) {
	        throw new RuntimeException("JSON 변환 중 오류 발생", e);
	    }
	}
	
	/**
	 * 요청 엔티티 생성
	 * @param jsonRequest
	 * @return
	 */
	private static HttpEntity<String> createHttpEntity(String jsonRequest) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    return new HttpEntity<>(jsonRequest, headers);
	}
}
