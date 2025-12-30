package com.financetracker.controller;

import com.financetracker.dto.ReportDTO;
import com.financetracker.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;

@Controller
@RequestMapping("/reports")
public class ReportController {
    
    private final ReportService reportService;
    
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    @GetMapping
    public String showReports(@RequestParam(required = false) String period, Model model) {
        YearMonth yearMonth = period != null && !period.isEmpty()
            ? YearMonth.parse(period)
            : YearMonth.now();
        
        ReportDTO report = reportService.generateMonthlyReport(yearMonth);
        
        model.addAttribute("report", report);
        model.addAttribute("currentPeriod", yearMonth.toString());
        
        return "reports/monthly";
    }
}