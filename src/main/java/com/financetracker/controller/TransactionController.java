package com.financetracker.controller;

import com.financetracker.dto.TransactionDTO;
import com.financetracker.model.Transaction;
import com.financetracker.model.TransactionType;
import com.financetracker.service.CategoryService;
import com.financetracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    
    public TransactionController(TransactionService transactionService,
                                CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }
    
    @GetMapping
    public String listTransactions(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model) {
        
        LocalDate start = startDate != null && !startDate.isEmpty() 
            ? LocalDate.parse(startDate) 
            : LocalDate.now().minusMonths(1);
        LocalDate end = endDate != null && !endDate.isEmpty() 
            ? LocalDate.parse(endDate) 
            : LocalDate.now();
        
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(start, end);
        
        model.addAttribute("transactions", transactions);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        
        return "transactions/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionDate(LocalDate.now());
        dto.setType(TransactionType.EXPENSE);
        
        model.addAttribute("transaction", dto);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("transactionTypes", TransactionType.values());
        
        return "transactions/form";
    }
    
    @PostMapping
    public String createTransaction(@Valid @ModelAttribute("transaction") TransactionDTO dto,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("transactionTypes", TransactionType.values());
            return "transactions/form";
        }
        
        transactionService.createTransaction(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction created successfully!");
        
        return "redirect:/transactions";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.getTransactionById(id);
        
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setCategoryId(transaction.getCategory().getId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setDescription(transaction.getDescription());
        dto.setRecurring(transaction.isRecurring());
        dto.setFrequency(transaction.getFrequency());
        
        model.addAttribute("transaction", dto);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("transactionTypes", TransactionType.values());
        
        return "transactions/form";
    }
    
    @PostMapping("/{id}")
    public String updateTransaction(@PathVariable Long id,
                                   @Valid @ModelAttribute("transaction") TransactionDTO dto,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("transactionTypes", TransactionType.values());
            return "transactions/form";
        }
        
        transactionService.updateTransaction(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction updated successfully!");
        
        return "redirect:/transactions";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteTransaction(@PathVariable Long id, 
                                   RedirectAttributes redirectAttributes) {
        transactionService.deleteTransaction(id);
        redirectAttributes.addFlashAttribute("successMessage", "Transaction deleted successfully!");
        
        return "redirect:/transactions";
    }
}