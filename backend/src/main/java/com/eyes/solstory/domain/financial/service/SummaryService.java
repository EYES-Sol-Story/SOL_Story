package com.eyes.solstory.domain.financial.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eyes.solstory.config.OpenApiUrls;
import com.eyes.solstory.domain.financial.dto.Transaction;
import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;
import com.eyes.solstory.util.CategoryClassifier;
import com.eyes.solstory.util.OpenApiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SummaryService {
	
	private static final Logger logger = LoggerFactory.getLogger(SummaryService.class);
	
	static final DateTimeFormatter DATE_FORMATTER= DateTimeFormatter.ofPattern("yyyyMMdd");
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    
    @Autowired
    private UserAccountService userAccountService;
	
    /*
	public static void main(String[] args) throws URISyntaxException {
		fetchAndStoreFinancialData();
	}
	*/
	
	/**
	 * 자정마다 모든 활성화 된 계좌의 전날 지출/저축 내역을 받아 summary 
	 * @throws URISyntaxException
	 */
	public void fetchAndStoreFinancialData() throws URISyntaxException {
	    // LocalDate yesterday = LocalDate.now().minusDays(1);
	    String yesterday = LocalDate.now().format(OpenApiUtil.DATE_FORMATTER);
	    
	    List<Object[]> userAccounts = userAccountService.findActiveAccounts();

	    
	    // 1. 신한은행 API를 호출하여 데이터를 가져옴
		/* List<Transaction> transactionList = */ fetchTransactionData(userAccounts, yesterday);
	    
//	    transactionList.forEach(System.out::println);

	    // 2. 거래 내역 별 사장님
	    // 2. 거래 내역을 카테고리별로 요약
	    //List<DailyFinancialSummary> summaries = summarizeByCategory(transactions);

	    // 3. 요약된 데이터를 데이터베이스에 저장
	    //storeSummaries(summaries);
	}
	
	/**
	 * 입출금 거래 내역 불러오기
	 * 
	 * @param yesterday 조회할 날짜
	 * @return 
	 * @throws URISyntaxException
	 */
	private void fetchTransactionData(List<Object[]> userAccounts, String yesterday) throws URISyntaxException{
        for (Object[] userAccount : userAccounts) {
        	int userNo = (int)userAccount[0];
        	String accountNo = (String)userAccount[1];
	        
        	Map<String, String> headerMap = OpenApiUtil.createHeaders(OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST);
	        Map<String, Object> requestMap = createTransactionHistoryRequestData(accountNo, yesterday, headerMap);
	
	        ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.DEMAND_DEPOSIT_URL + OpenApiUrls.INQUIRE_TRANSACTION_HISTORY_LIST), requestMap);
	
	        // 응답 데이터 처리
	        ObjectMapper objectMapper = new ObjectMapper();

	        List<Transaction> transactions;
	        try {
	            JsonNode rootNode = objectMapper.readTree(response.getBody());
	            JsonNode listNode = rootNode.path("REC").path("list");
	            transactions =  parseTransactionList(listNode);
	            
	        } catch (Exception e) {
	        	logger.error("응답에서 값 추출 중 오류 발생");
	            throw new RuntimeException("응답에서 값 추출 중 오류 발생", e);
	        }

	        fetchAndSetAccountHolderNames(userNo, transactions);
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
	private Map<String, Object> createTransactionHistoryRequestData(String accountNo, String yesterday, Map<String, String> headerMap) {
	    Map<String, Object> requestMap = new HashMap<>();
	    requestMap.put("Header", headerMap);
	    requestMap.put("accountNo", accountNo);
	    requestMap.put("startDate", yesterday); // 어제 날짜
	    requestMap.put("endDate", yesterday); // 어제 날짜
	    requestMap.put("transactionType", "D"); // 출금
	    requestMap.put("orderByType", "ASC");
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
	
	/**
	 * OpenApi 쓸 필요 없이 등록된 계좌 일리 없어서 Open Api 사용해야함
	 * 결제되어 입금된 계좌번호로 가게 상호명(userName) 받아오기
	 * 
	 * @param userNo 지출내역 검사 중인 사용자번호
	 * @param transactions 어제 지출 내역
	 * @throws URISyntaxException
	 */
	private void fetchAndSetAccountHolderNames(int userNo, List<Transaction> transactions) throws URISyntaxException{
		for(Transaction transaction : transactions) {
			Map<String, String> headerMap = OpenApiUtil.createHeaders(OpenApiUrls.INQUIRE_DEMAND_DEPOSIT_ACCOUNT_HOLDER_NAME);
			Map<String, Object> requestMap = createTransactionHistoryRequestData(transaction, headerMap);
			
			ResponseEntity<String> response = OpenApiUtil.callApi(new URI(OpenApiUrls.DEMAND_DEPOSIT_URL + OpenApiUrls.INQUIRE_DEMAND_DEPOSIT_ACCOUNT_HOLDER_NAME), requestMap);
			
			// 응답 데이터 처리
	        ObjectMapper objectMapper = new ObjectMapper();

	        try {
	            JsonNode rootNode = objectMapper.readTree(response.getBody());
	            String storeName = rootNode.path("REC").path("userName").asText();
	            transaction.setTransactionAccountName(storeName);
	        } catch (Exception e) {
	        	logger.error("응답에서 값 추출 중 오류 발생");
	            throw new RuntimeException("응답에서 값 추출 중 오류 발생", e);
	        }
		}
		
		// 상호명도 받았으면 카테고리별로 분석하러가야해
		
	}
	
	/**
	 * 사용자가 결제한 계좌 주인의 이름을 받아오기(상호명)
	 * 
	 * @param accountNo 조회할 계좌번호
	 * @param headerMap 요청 헤더
	 * @return requestMap
	 */
	private Map<String, Object> createTransactionHistoryRequestData(Transaction transaction, Map<String, String> headerMap) {
	    Map<String, Object> requestMap = new HashMap<>();
	    requestMap.put("Header", headerMap);
	    requestMap.put("accountNo", transaction.getTransactionAccountNo());
	    return requestMap;
	}
	
	
	/**
	 * 
	 * @param transactions
	 * @return
	 */
	private List<DailyFinancialSummary> categorizeAndSummarizeTransactions(int userNo, List<Transaction> transactions) {
	    Map<String, Integer> categoryTotals = new HashMap<>();
	    int totalAmount = 0;

	    for (Transaction transaction : transactions) {
	        String category = categorizeTransaction(transaction);
	        int amount = transaction.getTransactionBalance();
	        
	        categoryTotals.put(category, categoryTotals.getOrDefault(category, 0) + amount);
	        totalAmount += amount;
	    }
	    
	    CategoryClassifier.classify(null);

	    // 카테고리별 요약 정보를 DailyFinancialSummary 객체로 변환
	    List<DailyFinancialSummary> summaries = new ArrayList<>();
	    for (Map.Entry<String, Integer> entry : categoryTotals.entrySet()) {
	        String category = entry.getKey();
	        int amount = entry.getValue();

	        DailyFinancialSummary summary = DailyFinancialSummary.builder()
	        		.userNo(userNo)
	        		.financialDate(LocalDate.now())
	        		.financialType(2)
	        		.category(category)
	        		.totalAmount(totalAmount)
	        		.build();
	        		
	        summaries.add(summary);
	    }

	    return summaries;
	}

	private String categorizeTransaction(Transaction transaction) {
	    // 거래내역의 특정 조건에 따라 카테고리화하는 로직
	    // 예: 거래 내역의 설명, 금액, 거래처 등에 따라 카테고리를 결정
		String storeName = transaction.getTransactionAccountName();
	    if (storeName.contains("마트")) {
	        return "쇼핑";
	    } else if (storeName.contains("식당")) {
	        return "식사";
	    }
	    // 기타 조건들...

	    return "기타";
	}

	/*
	// 거래 내역을 카테고리별로 요약하는 메서드
	private List<DailyFinancialSummary> summarizeByCategory(List<Transaction> transactions) {
	    // 거래 내역을 카테고리별로 요약하는 로직 작성
	    return List.of(); // 임시로 빈 리스트 반환
	}

	// 요약된 데이터를 데이터베이스에 저장하는 메서드
	private void storeSummaries(List<DailyFinancialSummary> summaries) {
	    for (DailyFinancialSummary summary : summaries) {
	        summaryRepository.save(summary);
	    }
	}
	*/
	
	
	static String[] userAccounts = {
        "0017470988373838",
        "0016682622492948",
        "0018596693726863",
        "0010234910454945",
        "0016903722873721",
        "0011723549551059",
        "0017782776885238",
        "0011179031197410",
        "0015392003708693",
        "0014119531594391",
        "0016272229628944",
        "0018246995489613",
        "0013347196643518",
        "0017167160451081",
        "0011921035641664",
        "0012017014584537",
        "0016647443053332",
        "0013809631586899",
        "0016362049099558",
        "0016733982600184",
        "0019593990449808",
        "0019445234957916",
        "0013012739010203",
        "0017402124721631",
        "0014556338577405",
        "0013099678330648",
        "0011777674139413",
        "0018741333020193",
        "0016824449748046",
        "0014143629356398",
        "0012115852032889",
        "0011776828836074",
        "0017150074936247",
        "0018206935013690",
        "0011073967332414",
        "0017531347742322",
        "0011719364878441",
        "0017045865250958",
        "0015731313581919",
        "0014907125442508",
        "0018223898010344",
        "0013264290660241",
        "0017326326856792",
        "0018311876404559",
        "0012759423360365",
        "0017637732753675",
        "0014935349298216",
        "0013970040474359",
        "0010850080986986",
        "0018193495972741",
        "0014370737836272",
        "0010129260253019",
        "0019858284845604",
        "0014897435617175",
        "0014504045201014",
        "0013426757957855",
        "0011354800612650",
        "0017064522403640",
        "0016412855140906",
        "0016283494857135",
        "0010070379781130",
        "0015098620572472",
        "0018361012569317",
        "0018249362437940",
        "0015946819497707",
        "0017527814221866",
        "0018709159416161",
        "0017750592444247",
        "0019547658147645",
        "0010352014340062",
        "0015568242963460",
        "0015797768624405",
        "0014450664949875",
        "0019287272917828",
        "0016215247857767",
        "0018505863077827",
        "0019845207862658",
        "0016492544790374",
        "0014344261892106",
        "0017990895046431",
        "0011846587163886",
        "0016576596735762",
        "0011494255050340",
        "0016952566246050",
        "0019680859021112",
        "0011573548574786",
        "0012963410485827",
        "0015018813490727",
        "0012901677210432",
        "0019898478245723",
        "0015278769700355",
        "0016110448476493",
        "0018177533483182",
        "0010849369073567",
        "0015603752872571",
        "0011076988171146",
        "0016580244940828",
        "0010244435692161",
        "0015494523670506",
        "0011383263885659"
    };
}