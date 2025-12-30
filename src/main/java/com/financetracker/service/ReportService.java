package com.financetracker.service;

import com.financetracker.dto.ReportDTO;
import com.financetracker.model.Category;
import com.financetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportService {
    
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    
    public ReportService(TransactionRepository transactionRepository,
                        TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }
    
    public ReportDTO generateMonthlyReport(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        
        ReportDTO report = new ReportDTO();
        report.setPeriod(yearMonth.toString());
        
        BigDecimal totalIncome = transactionService.getTotalIncome(startDate, endDate);
        BigDecimal totalExpenses = transactionService.getTotalExpenses(startDate, endDate);
        
        report.setTotalIncome(totalIncome);
        report.setTotalExpenses(totalExpenses);
        report.calculateNetSavings();
        
        List<Object[]> categoryData = transactionRepository.getSpendingByCategory(startDate, endDate);
        
        List<ReportDTO.CategorySpending> breakdown = categoryData.stream()
            .map(data -> {
                Category category = (Category) data[0];
                BigDecimal amount = (BigDecimal) data[1];
                
                double percentage = 0.0;
                if (totalExpenses.compareTo(BigDecimal.ZERO) > 0) {
                    percentage = amount.divide(totalExpenses, 4, RoundingMode.HALF_UP)
                                     .multiply(BigDecimal.valueOf(100))
                                     .doubleValue();
                }
                
                return new ReportDTO.CategorySpending(
                    category.getName(),
                    amount,
                    percentage,
                    category.getColorCode()
                );
            })
            .collect(Collectors.toList());
        
        report.setCategoryBreakdown(breakdown);
        
        return report;
    }
    
    public ReportDTO generateDateRangeReport(LocalDate startDate, LocalDate endDate) {
        ReportDTO report = new ReportDTO();
        report.setPeriod(startDate + " to " + endDate);
        
        BigDecimal totalIncome = transactionService.getTotalIncome(startDate, endDate);
        BigDecimal totalExpenses = transactionService.getTotalExpenses(startDate, endDate);
        
        report.setTotalIncome(totalIncome);
        report.setTotalExpenses(totalExpenses);
        report.calculateNetSavings();
        
        List<Object[]> categoryData = transactionRepository.getSpendingByCategory(startDate, endDate);
        
        List<ReportDTO.CategorySpending> breakdown = categoryData.stream()
            .map(data -> {
                Category category = (Category) data[0];
                BigDecimal amount = (BigDecimal) data[1];
                
                double percentage = 0.0;
                if (totalExpenses.compareTo(BigDecimal.ZERO) > 0) {
                    percentage = amount.divide(totalExpenses, 4, RoundingMode.HALF_UP)
                                     .multiply(BigDecimal.valueOf(100))
                                     .doubleValue();
                }
                
                return new ReportDTO.CategorySpending(
                    category.getName(),
                    amount,
                    percentage,
                    category.getColorCode()
                );
            })
            .collect(Collectors.toList());
        
        report.setCategoryBreakdown(breakdown);
        
        return report;
    }
}