package com.financetracker.service;

import com.financetracker.dto.TransactionDTO;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import com.financetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    
    public TransactionService(TransactionRepository transactionRepository,
                            CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end) {
        return transactionRepository.findByTransactionDateBetween(start, end);
    }
    
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }
    
    public Transaction createTransaction(TransactionDTO dto) {
        Category category = categoryService.getCategoryById(dto.getCategoryId());
        
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setCategory(category);
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setDescription(dto.getDescription());
        transaction.setRecurring(dto.isRecurring());
        transaction.setFrequency(dto.getFrequency());
        
        return transactionRepository.save(transaction);
    }
    
    public Transaction updateTransaction(Long id, TransactionDTO dto) {
        Transaction transaction = getTransactionById(id);
        Category category = categoryService.getCategoryById(dto.getCategoryId());
        
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setCategory(category);
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setDescription(dto.getDescription());
        transaction.setRecurring(dto.isRecurring());
        transaction.setFrequency(dto.getFrequency());
        
        return transactionRepository.save(transaction);
    }
    
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
    
    public BigDecimal getBalance() {
        BigDecimal income = transactionRepository.sumByTypeAndDateRange(
            TransactionType.INCOME, 
            LocalDate.of(2000, 1, 1), 
            LocalDate.now()
        );
        BigDecimal expenses = transactionRepository.sumByTypeAndDateRange(
            TransactionType.EXPENSE, 
            LocalDate.of(2000, 1, 1), 
            LocalDate.now()
        );
        
        return (income != null ? income : BigDecimal.ZERO)
            .subtract(expenses != null ? expenses : BigDecimal.ZERO);
    }
    
    public BigDecimal getTotalIncome(LocalDate start, LocalDate end) {
        BigDecimal total = transactionRepository.sumByTypeAndDateRange(
            TransactionType.INCOME, start, end
        );
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalExpenses(LocalDate start, LocalDate end) {
        BigDecimal total = transactionRepository.sumByTypeAndDateRange(
            TransactionType.EXPENSE, start, end
        );
        return total != null ? total : BigDecimal.ZERO;
    }
}