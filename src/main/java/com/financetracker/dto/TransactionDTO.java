package com.financetracker.dto;

import com.financetracker.model.RecurringFrequency;
import com.financetracker.model.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {
    
    private Long id;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
    
    private String categoryName;
    
    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    private boolean isRecurring = false;
    
    private RecurringFrequency frequency = RecurringFrequency.NONE;
    
    public TransactionDTO() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
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
    
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isRecurring() {
        return isRecurring;
    }
    
    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
    
    public RecurringFrequency getFrequency() {
        return frequency;
    }
    
    public void setFrequency(RecurringFrequency frequency) {
        this.frequency = frequency;
    }
}