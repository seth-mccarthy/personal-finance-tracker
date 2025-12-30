package com.financetracker.repository;

import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByTransactionDateBetween(LocalDate start, LocalDate end);
    
    List<Transaction> findByCategory(Category category);
    
    List<Transaction> findByType(TransactionType type);
    
    List<Transaction> findByIsRecurringTrue();
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumByTypeAndDateRange(@Param("type") TransactionType type, 
                                     @Param("start") LocalDate start, 
                                     @Param("end") LocalDate end);
    
    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
           "WHERE t.type = 'EXPENSE' AND t.transactionDate BETWEEN :start AND :end " +
           "GROUP BY t.category")
    List<Object[]> getSpendingByCategory(@Param("start") LocalDate start, 
                                         @Param("end") LocalDate end);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t " +
           "WHERE t.type = 'EXPENSE' AND t.category = :category " +
           "AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumByCategoryAndDateRange(@Param("category") Category category,
                                         @Param("start") LocalDate start,
                                         @Param("end") LocalDate end);
}