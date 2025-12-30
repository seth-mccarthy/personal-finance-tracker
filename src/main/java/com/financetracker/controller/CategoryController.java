package com.financetracker.controller;

import com.financetracker.model.Category;
import com.financetracker.model.TransactionType;
import com.financetracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("transactionTypes", TransactionType.values());
        return "categories/form";
    }
    
    @PostMapping
    public String createCategory(@Valid @ModelAttribute Category category,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("transactionTypes", TransactionType.values());
            return "categories/form";
        }
        
        try {
            categoryService.createCategory(category);
            redirectAttributes.addFlashAttribute("successMessage", "Category created successfully!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("transactionTypes", TransactionType.values());
            return "categories/form";
        }
        
        return "redirect:/categories";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        model.addAttribute("transactionTypes", TransactionType.values());
        return "categories/form";
    }
    
    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Long id,
                                @Valid @ModelAttribute Category category,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("transactionTypes", TransactionType.values());
            return "categories/form";
        }
        
        categoryService.updateCategory(id, category);
        redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully!");
        
        return "redirect:/categories";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, 
                                RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Cannot delete category. It may be in use by transactions or budgets.");
        }
        
        return "redirect:/categories";
    }
}