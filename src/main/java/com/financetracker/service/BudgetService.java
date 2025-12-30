package com.financetracker.service;

import com.financetracker.dto.BudgetDTO;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.model.Budget;
import com.financetracker.model.Category;
import com.financetracker.repository.BudgetRepository;
import com.financetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BudgetService {
    
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    
    public BudgetService(BudgetRepository budgetRepository,
                        TransactionRepository transactionRepository,
                        CategoryService categoryService) {
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
    }
    
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
    
    public List<Budget> getBudgetsByPeriod(String period) {
        List<Budget> budgets = budgetRepository.findByPeriod(period);
        budgets.forEach(this::calculateSpentAmount);
        return budgets;
    }
    
    public Budget getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        calculateSpentAmount(budget);
        return budget;
    }
    
    public Budget createBudget(BudgetDTO dto) {
        Category category = categoryService.getCategoryById(dto.getCategoryId());
        
        if (budgetRepository.existsByCategoryAndPeriod(category, dto.getPeriod())) {
            throw new IllegalArgumentException(
                "Budget already exists for category '" + category.getName() + 
                "' in period " + dto.getPeriod()
            );
        }
        
        Budget budget = new Budget();
        budget.setCategory(category);
        budget.setLimitAmount(dto.getLimitAmount());
        budget.setPeriod(dto.getPeriod());
        
        return budgetRepository.save(budget);
    }
    
    public Budget updateBudget(Long id, BudgetDTO dto) {
        Budget budget = getBudgetById(id);
        Category category = categoryService.getCategoryById(dto.getCategoryId());
        
        budget.setCategory(category);
        budget.setLimitAmount(dto.getLimitAmount());
        budget.setPeriod(dto.getPeriod());
        
        return budgetRepository.save(budget);
    }
    
    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }
    
    private void calculateSpentAmount(Budget budget) {
        YearMonth yearMonth = budget.getPeriodAsYearMonth();
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        
        BigDecimal spent = transactionRepository.sumByCategoryAndDateRange(
            budget.getCategory(), startDate, endDate
        );
        
        budget.calculateSpentAndRemaining(spent);
    }
    
    public List<Budget> getBudgetsNearingLimit(String period, int thresholdPercentage) {
        return getBudgetsByPeriod(period).stream()
            .filter(b -> b.getPercentageUsed() >= thresholdPercentage)
            .collect(Collectors.toList());
    }
}