package com.eyes.solstory.domain.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.dto.CategorySpendingSummaryDTO;
import com.eyes.solstory.domain.financial.repository.FinancialSummaryRepository;

/**
 * 금융 정보 분석
 */
@Service
public class FinancialSummaryAnalyzer {
	@Autowired
    private FinancialSummaryRepository financialSummaryRepository;

    public List<CategorySpendingSummaryDTO> getTopCategories(int userNo) {
        return financialSummaryRepository.findTopCategoriesByUser(userNo);
    }
}
