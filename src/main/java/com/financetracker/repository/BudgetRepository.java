package com.financetracker.repository;

import com.financetracker.model.Budget;
import com.financetracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    
    List<Budget> findByPeriod(String period);
    
    Optional<Budget> findByCategoryAndPeriod(Category category, String period);
    
    boolean existsByCategoryAndPeriod(Category category, String period);
}