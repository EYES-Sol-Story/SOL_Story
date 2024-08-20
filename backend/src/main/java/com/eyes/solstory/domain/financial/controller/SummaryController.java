package com.eyes.solstory.domain.financial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eyes.solstory.domain.financial.service.SummaryService;

@RestController
@RequestMapping("/api/financial/summaries")
public class SummaryController {
	
	@Autowired
	private SummaryService summaryService;
	
	
}
