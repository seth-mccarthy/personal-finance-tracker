package com.financetracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    
    @Column(name = "color_code")
    private String colorCode;
    
    public Category() {
    }
    
    public Category(String name, TransactionType type) {
        this.name = name;
        this.type = type;
    }
    
    public Category(String name, String description, TransactionType type, String colorCode) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.colorCode = colorCode;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public String getColorCode() {
        return colorCode;
    }
    
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}