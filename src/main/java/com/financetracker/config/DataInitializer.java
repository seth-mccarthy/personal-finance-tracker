package com.financetracker.config;

import com.financetracker.model.Category;
import com.financetracker.model.TransactionType;
import com.financetracker.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final CategoryRepository categoryRepository;
    
    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            // Income categories
            categoryRepository.save(new Category("Salary", "Monthly salary", TransactionType.INCOME, "#10b981"));
            categoryRepository.save(new Category("Freelance", "Freelance income", TransactionType.INCOME, "#3b82f6"));
            categoryRepository.save(new Category("Investments", "Investment returns", TransactionType.INCOME, "#8b5cf6"));
            categoryRepository.save(new Category("Other Income", "Miscellaneous income", TransactionType.INCOME, "#6366f1"));
            
            // Expense categories
            categoryRepository.save(new Category("Groceries", "Food and groceries", TransactionType.EXPENSE, "#ef4444"));
            categoryRepository.save(new Category("Rent", "Monthly rent", TransactionType.EXPENSE, "#f59e0b"));
            categoryRepository.save(new Category("Utilities", "Electricity, water, internet", TransactionType.EXPENSE, "#eab308"));
            categoryRepository.save(new Category("Transportation", "Gas, public transit", TransactionType.EXPENSE, "#06b6d4"));
            categoryRepository.save(new Category("Entertainment", "Movies, games, hobbies", TransactionType.EXPENSE, "#ec4899"));
            categoryRepository.save(new Category("Healthcare", "Medical expenses", TransactionType.EXPENSE, "#14b8a6"));
            categoryRepository.save(new Category("Shopping", "Clothes, electronics", TransactionType.EXPENSE, "#a855f7"));
            categoryRepository.save(new Category("Dining Out", "Restaurants, cafes", TransactionType.EXPENSE, "#f97316"));
            categoryRepository.save(new Category("Other Expenses", "Miscellaneous", TransactionType.EXPENSE, "#64748b"));
            
            System.out.println("Sample categories initialized!");
        }
    }
}