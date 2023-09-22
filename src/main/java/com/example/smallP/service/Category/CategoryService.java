package com.example.smallP.service.Category;

import com.example.smallP.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById (int theId);

    Category save (Category theCategory);

    void deleteById(int theId);
}
