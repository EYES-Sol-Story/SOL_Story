package com.eyes.solstory.domain.financial.controller;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.financial.service.SummaryService;

// test용 컨트롤러
@RestController
@RequestMapping("/api/test")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    /**
     * SummaryService의 fetchAndStoreFinancialData 메서드를 호출하여 테스트합니다.
     */
    @PostMapping("/fetch-and-store")
    public ResponseEntity<String> testFetchAndStoreFinancialData() {
        try {
            summaryService.fetchAndStoreFinancialData();
            return ResponseEntity.ok("Financial data fetch and store process completed successfully.");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }
}