package com.eyes.solstory.domain.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.financial.entity.DailyFinancialSummary;

@Repository
public interface SummaryRepository extends JpaRepository<DailyFinancialSummary, Integer> {
	
}
