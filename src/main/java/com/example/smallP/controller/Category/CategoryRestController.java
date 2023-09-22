package com.example.smallP.controller.Category;

import com.example.smallP.entity.Category;
import com.example.smallP.service.Category.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryRestController {
    private CategoryService categoryService;


    public CategoryRestController(CategoryService theCategoryService){
        categoryService = theCategoryService;
    }

    @GetMapping("/category")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/category/{id}")
    public Category getCategory(@PathVariable int categoryId){
        Category theCategory = categoryService.findById(categoryId);

        if (theCategory == null){
            throw new RuntimeException("Category id not found -" +theCategory );
        }
        else {
            return theCategory;
        }
    }

    @PostMapping("/category")
    public Category addCategory(@RequestBody Category theCategory){

        theCategory.setId(0);

        Category dbCategory = categoryService.save(theCategory);

        return dbCategory;
    }

    @PutMapping("/category")
    public Category updateCategory(@RequestBody Category theCategory){

        Category dbCategory = categoryService.save(theCategory);
        return  dbCategory;
    }

    @DeleteMapping("/category/{categoryId}")
    public String delete(@PathVariable int categoryId){

        Category tempUser = categoryService.findById(categoryId);

        if (tempUser == null){
            throw new RuntimeException("Category not found -" +categoryId);
        }

        categoryService.deleteById(categoryId);

        return "Delete Category with id- " +categoryId;
    }

}
