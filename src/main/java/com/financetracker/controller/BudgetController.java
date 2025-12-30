package com.financetracker.controller;

import com.financetracker.dto.BudgetDTO;
import com.financetracker.model.Budget;
import com.financetracker.model.TransactionType;
import com.financetracker.service.BudgetService;
import com.financetracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
    
    private final BudgetService budgetService;
    private final CategoryService categoryService;
    
    public BudgetController(BudgetService budgetService,
                           CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }
    
    @GetMapping
    public String listBudgets(@RequestParam(required = false) String period, Model model) {
        String currentPeriod = period != null && !period.isEmpty() 
            ? period 
            : YearMonth.now().toString();
        
        List<Budget> budgets = budgetService.getBudgetsByPeriod(currentPeriod);
        
        model.addAttribute("budgets", budgets);
        model.addAttribute("currentPeriod", currentPeriod);
        
        return "budgets/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        BudgetDTO dto = new BudgetDTO();
        dto.setPeriod(YearMonth.now().toString());
        
        model.addAttribute("budget", dto);
        model.addAttribute("categories", categoryService.getCategoriesByType(TransactionType.EXPENSE));
        
        return "budgets/form";
    }
    
    @PostMapping
    public String createBudget(@Valid @ModelAttribute("budget") BudgetDTO dto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoriesByType(TransactionType.EXPENSE));
            return "budgets/form";
        }
        
        try {
            budgetService.createBudget(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Budget created successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("categories", categoryService.getCategoriesByType(TransactionType.EXPENSE));
            return "budgets/form";
        }
        
        return "redirect:/budgets?period=" + dto.getPeriod();
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Budget budget = budgetService.getBudgetById(id);
        
        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setCategoryId(budget.getCategory().getId());
        dto.setLimitAmount(budget.getLimitAmount());
        dto.setPeriod(budget.getPeriod());
        
        model.addAttribute("budget", dto);
        model.addAttribute("categories", categoryService.getCategoriesByType(TransactionType.EXPENSE));
        
        return "budgets/form";
    }
    
    @PostMapping("/{id}")
    public String updateBudget(@PathVariable Long id,
                              @Valid @ModelAttribute("budget") BudgetDTO dto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoriesByType(TransactionType.EXPENSE));
            return "budgets/form";
        }
        
        budgetService.updateBudget(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Budget updated successfully!");
        
        return "redirect:/budgets?period=" + dto.getPeriod();
    }
    
    @PostMapping("/{id}/delete")
    public String deleteBudget(@PathVariable Long id,
                              @RequestParam String period,
                              RedirectAttributes redirectAttributes) {
        budgetService.deleteBudget(id);
        redirectAttributes.addFlashAttribute("successMessage", "Budget deleted successfully!");
        
        return "redirect:/budgets?period=" + period;
    }
}