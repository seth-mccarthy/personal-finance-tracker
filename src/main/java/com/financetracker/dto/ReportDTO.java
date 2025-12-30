package com.financetracker.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReportDTO {
    
    private String period;
    private BigDecimal totalIncome = BigDecimal.ZERO;
    private BigDecimal totalExpenses = BigDecimal.ZERO;
    private BigDecimal netSavings = BigDecimal.ZERO;
    private List<CategorySpending> categoryBreakdown = new ArrayList<>();
    
    public ReportDTO() {
    }
    
    public static class CategorySpending {
        private String categoryName;
        private BigDecimal amount;
        private double percentage;
        private String colorCode;
        
        public CategorySpending() {
        }
        
        public CategorySpending(String categoryName, BigDecimal amount, double percentage, String colorCode) {
            this.categoryName = categoryName;
            this.amount = amount;
            this.percentage = percentage;
            this.colorCode = colorCode;
        }
        
        // Getters and Setters
        public String getCategoryName() {
            return categoryName;
        }
        
        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
        
        public BigDecimal getAmount() {
            return amount;
        }
        
        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
        
        public double getPercentage() {
            return percentage;
        }
        
        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }
        
        public String getColorCode() {
            return colorCode;
        }
        
        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }
    }
    
    public void calculateNetSavings() {
        this.netSavings = totalIncome.subtract(totalExpenses);
    }
    
    // Getters and Setters
    public String getPeriod() {
        return period;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public BigDecimal getTotalIncome() {
        return totalIncome;
    }
    
    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
    
    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }
    
    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
    
    public BigDecimal getNetSavings() {
        return netSavings;
    }
    
    public void setNetSavings(BigDecimal netSavings) {
        this.netSavings = netSavings;
    }
    
    public List<CategorySpending> getCategoryBreakdown() {
        return categoryBreakdown;
    }
    
    public void setCategoryBreakdown(List<CategorySpending> categoryBreakdown) {
        this.categoryBreakdown = categoryBreakdown;
    }
}