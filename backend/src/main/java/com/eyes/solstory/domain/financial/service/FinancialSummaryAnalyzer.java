package com.eyes.solstory.domain.financial.service;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.dto.AccountKeyDTO;
import com.eyes.solstory.domain.financial.dto.CategorySpendingAvgDTO;
import com.eyes.solstory.domain.financial.dto.CategorySpendingSummaryDTO;
import com.eyes.solstory.domain.financial.dto.FinancialTrendDTO;
import com.eyes.solstory.domain.financial.dto.StoreSpendingSummary;
import com.eyes.solstory.domain.financial.dto.UserCategoryDTO;
import com.eyes.solstory.domain.financial.repository.FinancialSummaryRepository;
import com.eyes.solstory.domain.financial.repository.UserAccountRepository;

import lombok.AllArgsConstructor;

/**
 * 금융 정보 분석
 */
@SuppressWarnings("unused")
@Service
@AllArgsConstructor
public class FinancialSummaryAnalyzer {
	
	private SpendingSummaryProcessor spendingProcessor; 
	private SavingsCollector savingsCollector;
    private FinancialSummaryRepository summaryRepository;
	private UserAccountRepository accountRepository;

	/**
	 * 최근 한달 지출 상위 5개 카테고리
	 * @param userNo
	 * @return
	 */
    public List<CategorySpendingSummaryDTO> getTop5Categories(int userNo) {
    	return summaryRepository.findTop5Categories(userNo);
    }
    
    /**
     * 최근 한달 지출 상위 5개 카테고리의 동일 연령대 평균 지출 금액
     * @param userNo
     * @return
     */
    public Map<String, CategorySpendingAvgDTO> getTop5CategoriesWithAvg(int userNo) {
    	List<CategorySpendingAvgDTO> list = summaryRepository.findTop5CategoriesWithAvg(userNo);
    	Map<String, CategorySpendingAvgDTO> map = new HashMap<>();
    	for(CategorySpendingAvgDTO others : list) {
    		map.put(others.getCategory(), others);
    	}
        return map;
    }
    
    /**
     * 최근 한달 지출 상위 10개 카테고리의 전월 대비 지출 증감
     * @param userNo
     * @return
     */
    public List<FinancialTrendDTO> getSpendingTrends(int userNo) {
    	return summaryRepository.getSpendingTrends(userNo);
    }
    
    /**
     * 최근 7일 전체 카테고리별 지출
     * @param userNo
     * @return
     */
    public List<CategorySpendingSummaryDTO> getLast7DaysSpending(int userNo) {
    	return summaryRepository.getLast7DaysSpending(userNo);
    }
    
    /**
     * 최근 7일간 가장 지출이 많은 카테고리의 한달간 지출 내역 요약
     * @param userNo
     * @return
     * @throws URISyntaxException 
     */
    public List<StoreSpendingSummary> getCategoryDetails(int userNo) throws URISyntaxException {
    	// 최근 7일간 가장 지출이 많은 카테고리와, 입출금 계좌번호, user_key 받아오기
    	UserCategoryDTO categoryDTO = summaryRepository.getMostSpendingCategory(userNo);
    	// 최근 30일간 지출처별 지출 내역 요약 정보
    	return spendingProcessor.fetchTransactionDataForMonth(categoryDTO);
    }
    
    
    /** 미사용
     * 최근 한달, 전월 대비 소비 증가율이 가장 높은 카테고리 중 가장 지출이 높은 keyword 반환
     * @param userNo
     * @return
     * @throws URISyntaxException
     */
    public String getKeywordWithHighestSpendingGrowth(int userNo) throws URISyntaxException {
    	// 최근 한달, 전월 대비 소비 증가율이 가장 높은 카테고리, 입출금 계좌번호, user_key 받아오기
    	UserCategoryDTO categoryDTO = summaryRepository.getCategoryWithHighestSpendingGrowth(userNo);
    	// 최근 30일간 지출처별 지출 내역 요약 정보
    	return spendingProcessor.getKeywordWithCategoryForMonth(categoryDTO);
    }
    
    /**
     * 최근 한달, 전월 대비 소비 증가율이 가장 높은 카테고리
     * @param userNo
     * @return
     * @throws URISyntaxException
     */
    public String getCategoryWithHighestSpendingGrowth(int userNo) throws URISyntaxException {
    	return  summaryRepository.findTopCategoryForMonth(userNo);
    }
    /**
     * 최근 한달 지출 총액 > DB에서 합산으로 가져옴
     * @param userNo
     * @return 최근 한달 지출 총액
     */
    public int getTotalSpendingForMonth(int userNo) {
    	return summaryRepository.deriveTotalSpendingForMonth(userNo);
    }
    
    /**
     * 여태까지 저축 총액 (계좌 잔액으로)
     * @param userNo
     * @return
     * @throws URISyntaxException 
     */
    public int getTotalSavingsAmount(int userNo) throws URISyntaxException {
    	// 저축 계좌 번호, user_key를 받아옴
    	AccountKeyDTO userAccount = summaryRepository.findActiveSavingsAccounts(userNo);
    	return savingsCollector.fetchSavingsTotal(userAccount);
    }
    
    /**
     * 금융 점수
     * @param userNo
     * @return
     */
    public int getFinancialScore(int userNo) {
    	return summaryRepository.deriveFinancialScore(userNo);
    }
    
    
    
    /**
     * 최근 한달 지출 상위 3개 카테고리
     * @param userNo
     * @return
     */
    public String[] getTop3Categories(int userNo) {
    	return summaryRepository.findTop3Categories(userNo);
    }
}
