package com.eyes.solstory.domain.financial.service;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.dto.CategorySpendingSummaryDTO;
import com.eyes.solstory.domain.financial.dto.StoreSpendingSummaryDTO;
import com.eyes.solstory.domain.financial.repository.FinancialSummaryRepository;

/**
 * 금융 정보 분석
 */
@Service
public class FinancialSummaryAnalyzer {
	
	@Autowired
	private SpendingSummaryProcessor spendingProcessor; 
	
	@Autowired
    private FinancialSummaryRepository summaryRepository;

	/**
	 * 최근 한달 지출 상위 5개 카테고리
	 * @param userNo
	 * @return
	 */
    public List<CategorySpendingSummaryDTO> getTopCategories(int userNo) {
    	return convertToDTO1(summaryRepository.findTopCategoriesByUser(userNo));
    }
    
    /**
     * 최근 한달 지출 상위 5개 카테고리의 동일 연령대 평균 지출 금액
     * @param userNo
     * @return
     */
    public Map<String, CategorySpendingSummaryDTO> getTop5CategoriesWithAvg(int userNo) {
        return convertToDTO2(summaryRepository.findTopCategoriesWithAvg(userNo));
    }
    
    /**
     * 최근 한달 지출 상위 10개 카테고리의 전월 대비 지출 증감
     * @param userNo
     * @return
     */
    public List<CategorySpendingSummaryDTO> getSpendingTrends(int userNo) {
    	return convertToDTO3(summaryRepository.getSpendingTrends(userNo));
    }
    
    /**
     * 최근 7일 전체 카테고리별 지출
     * @param userNo
     * @return
     */
    public List<CategorySpendingSummaryDTO> getLast7DaysSpending(int userNo) {
    	return convertToDTO1(summaryRepository.getLast7DaysSpending(userNo));
    }
    
    /**
     * 최근 7일간 가장 지출이 많은 카테고리의 한달간 지출 내역 요약
     * @param userNo
     * @return
     * @throws URISyntaxException 
     */
    public List<StoreSpendingSummaryDTO> getCategoryDetails(int userNo) throws URISyntaxException {
    	// 최근 7일간 가장 지출이 많은 카테고리와, 입출금 계좌번호, user_key 받아오기
    	Object[] obj = summaryRepository.getUserMostSpendingCategory(userNo);
    	// 최근 30일간 지출처별 지출 내역 요약 정보
    	return spendingProcessor.fetchTransactionDataForMonth(obj);
    }
    
    
    /**
     * 최근 한달, 전월 대비 소비 증가율이 가장 높은 카테고리 중 가장 지출이 높은 keyword 반환
     * @param userNo
     * @return
     * @throws URISyntaxException
     */
    public String getKeywordWithHighestSpendingGrowth(int userNo) throws URISyntaxException {
    	// 최근 한달, 전월 대비 소비 증가율이 가장 높은 카테고리, 입출금 계좌번호, user_key 받아오기
    	Object[] obj = summaryRepository.getCategoryWithHighestSpendingGrowth(userNo);
    	// 최근 30일간 지출처별 지출 내역 요약 정보
    	return spendingProcessor.getKeywordWithCategoryForMonth(obj);
    }
    
    
    
    private List<CategorySpendingSummaryDTO> convertToDTO1(List<Object[]> results) {
    	return results.stream()
    			.map(row -> {
    				CategorySpendingSummaryDTO dto = CategorySpendingSummaryDTO.builder()
    						.category((String) row[0]) // category
    						.totalAmount(((Number) row[1]).intValue())  // totalAmount
    						.build();
    				return dto;
    			})
    			.collect(Collectors.toList());
    }
    
    private Map<String, CategorySpendingSummaryDTO> convertToDTO2(List<Object[]> results) {
    	Map<String, CategorySpendingSummaryDTO> map = new HashMap<>();
    	for(Object[] row : results) {
    		CategorySpendingSummaryDTO dto = CategorySpendingSummaryDTO.builder()
    				.category((String) row[0]) //category
    				.avgAmount(((Number) row[1]).intValue()) //avg_amount
    				.ageGroup((String) row[2]) //age_group
    				.build();
    		map.put((String) row[0], dto);
    	}
    	return map;
    }
    
    private List<CategorySpendingSummaryDTO> convertToDTO3(List<Object[]> results) {
    	return results.stream()
    			.map(row -> {
    				CategorySpendingSummaryDTO dto = CategorySpendingSummaryDTO.builder()
    						.category((String) row[0]) // category
    						.totalAmount(((Number) row[1]).intValue())  // totalAmount(after)
    						.totalAmountBefore(((Number) row[2]).intValue())  // 비교군
    						.difference(((Number) row[3]).intValue())  // 증감액
    						.percentChange(((Number) row[4]).doubleValue())  // 증감률
    						.build();
    				return dto;
    			})
    			.collect(Collectors.toList());
    }
    
}
