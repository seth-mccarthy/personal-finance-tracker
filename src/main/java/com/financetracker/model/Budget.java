package com.financetracker.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;

@Entity
@Table(name = "budgets")
public class Budget {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Column(name = "limit_amount", nullable = false)
    private BigDecimal limitAmount;
    
    @Column(nullable = false)
    private String period; // Format: YYYY-MM
    
    @Transient
    private BigDecimal spent = BigDecimal.ZERO;
    
    @Transient
    private BigDecimal remaining = BigDecimal.ZERO;
    
    public Budget() {
    }
    
    public YearMonth getPeriodAsYearMonth() {
        return YearMonth.parse(period);
    }
    
    public void setPeriodFromYearMonth(YearMonth yearMonth) {
        this.period = yearMonth.toString();
    }
    
    public void calculateSpentAndRemaining(BigDecimal spentAmount) {
        this.spent = spentAmount != null ? spentAmount : BigDecimal.ZERO;
        this.remaining = limitAmount.subtract(this.spent);
    }
    
    public boolean isOverBudget() {
        return remaining.compareTo(BigDecimal.ZERO) < 0;
    }
    
    public int getPercentageUsed() {
        if (limitAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        return spent.multiply(BigDecimal.valueOf(100))
                    .divide(limitAmount, 0, RoundingMode.HALF_UP)
                    .intValue();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
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
}