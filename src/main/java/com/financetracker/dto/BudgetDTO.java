package com.financetracker.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class BudgetDTO {
    
    private Long id;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
    
    private String categoryName;
    
    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.01", message = "Limit must be greater than 0")
    private BigDecimal limitAmount;
    
    @NotNull(message = "Period is required")
    private String period; // Format: YYYY-MM
    
    private BigDecimal spent;
    private BigDecimal remaining;
    private int percentageUsed;
    private boolean overBudget;
    
    public BudgetDTO() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public BigDecimal getLimitAmount() {
        return limitAmount;
    }
    
    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }
    
    public String getPeriod() {
        return period;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public BigDecimal getSpent() {
        return spent;
    }
    
    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }
    
    public BigDecimal getRemaining() {
        return remaining;
    }
    
    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }
    
    public int getPercentageUsed() {
        return percentageUsed;
    }
    
    public void setPercentageUsed(int percentageUsed) {
        this.percentageUsed = percentageUsed;
    }
    
    public boolean isOverBudget() {
        return overBudget;
    }
    
    public void setOverBudget(boolean overBudget) {
        this.overBudget = overBudget;
    }
}