package com.eyes.solstory.domain.financial.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.financial.dto.CategorySpendingSummaryDTO;
import com.eyes.solstory.domain.financial.service.FinancialSummaryAnalyzer;
import com.eyes.solstory.domain.financial.service.FinancialSummaryProcessor;


@RestController
@RequestMapping("/api/financial/summary")
public class FinancialSummaryController {

    @Autowired
    private FinancialSummaryProcessor summaryProcessor;
    @Autowired
    private FinancialSummaryAnalyzer summaryAnalyzer;

    /**
     * 테스트용
     * SummaryService의 fetchAndStoreFinancialData 메서드를 호출하여 테스트합니다.
     */
    @PostMapping("/test/fetch-and-store")
    public ResponseEntity<String> testFetchAndStoreFinancialData() {
        try {
        	summaryProcessor.fetchAndStoreFinancialData();
            return ResponseEntity.ok("Financial data fetch and store process completed successfully.");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }
    
    @PostMapping("/top5-categories")
    public ResponseEntity<List<CategorySpendingSummaryDTO>> getTopCategories(@RequestParam int userNo) {
        List<CategorySpendingSummaryDTO> results = summaryAnalyzer.getTopCategories(userNo);
        return ResponseEntity.ok(results);
    }
    
    @PostMapping("/top5-categories-with-avg")
    public ResponseEntity<String> getTop5CategoriesWithAvg(){
    	return null;
    }
    
    @PostMapping("/spending-trends")
    public ResponseEntity<String> getSpendingTrends(){
    	return null;
    }
    @PostMapping("/last7-days-spending")
    public ResponseEntity<String> getLast7DaysSpending(){
    	return null;
    }
    
    @PostMapping("/category-details/{category}")
    public ResponseEntity<String> getCategoryDetails(@PathVariable String category){
    	return null;
    }
}