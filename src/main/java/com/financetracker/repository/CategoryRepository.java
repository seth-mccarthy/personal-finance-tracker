package com.financetracker.repository;

import com.financetracker.model.Category;
import com.financetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByType(TransactionType type);
    
    Optional<Category> findByName(String name);
    
    boolean existsByName(String name);
}