package com.example.smallP.service.Category;

import com.example.smallP.dao.Category.CategoryDAO;
import com.example.smallP.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO theCategory){
        categoryDAO = theCategory;
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Category findById(int theId) {
        return categoryDAO.findById(theId);
    }

    @Transactional // you chance something in database
    @Override
    public Category save(Category theCategory) {
        return categoryDAO.save(theCategory);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        categoryDAO.deleteById(theId);
    }
}
