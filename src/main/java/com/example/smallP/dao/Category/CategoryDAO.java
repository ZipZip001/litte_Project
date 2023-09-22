package com.example.smallP.dao.Category;

import com.example.smallP.entity.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> findAll();

    Category findById (int theId);

    Category save (Category theCategory);

    void deleteById(int theId);
}
