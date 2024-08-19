package com.eyes.solstory.domain.financial.entitycom.eyes.solstory.domain.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;
import com.eyes.solstory.domain.financial.repository.SummaryRepository;

@Service
public class SummaryService {
	
	@Autowired
	private SummaryRepository summaryRepository;
	
	public DailyFinancialSummary saveSummary(DailyFinancialSummary summary) {
        return summaryRepository.save(summary);
    }
}
