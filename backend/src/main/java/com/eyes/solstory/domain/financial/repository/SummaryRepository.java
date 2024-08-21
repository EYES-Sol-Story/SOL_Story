package com.eyes.solstory.domain.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;

public interface SummaryRepository extends JpaRepository<DailyFinancialSummary, Integer> {
	
}
