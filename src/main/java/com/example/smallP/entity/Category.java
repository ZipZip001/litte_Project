package com.example.smallP.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name= "category")
    private String category;

    // default constructor
    public Category(){

    }

    // Constructor


    public Category(String category) {
        this.category = category;
    }

    // getter & setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
