package com.financetracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.financetracker.model.Transaction;
import com.financetracker.service.TransactionService;

@Controller
public class HomeController {
    
    private final TransactionService transactionService;
    
    public HomeController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        BigDecimal balance = transactionService.getBalance();
        
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now();
        
        BigDecimal monthlyIncome = transactionService.getTotalIncome(startOfMonth, endOfMonth);
        BigDecimal monthlyExpenses = transactionService.getTotalExpenses(startOfMonth, endOfMonth);
        
        List<Transaction> recentTransactions = transactionService
            .getTransactionsByDateRange(LocalDate.now().minusDays(7), LocalDate.now());
        
        model.addAttribute("balance", balance);
        model.addAttribute("monthlyIncome", monthlyIncome);
        model.addAttribute("monthlyExpenses", monthlyExpenses);
        model.addAttribute("recentTransactions", recentTransactions);
        
        return "index";
    }
}