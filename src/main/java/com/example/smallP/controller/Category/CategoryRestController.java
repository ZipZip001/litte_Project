package com.example.smallP.controller.Category;

import com.example.smallP.entity.Category;
import com.example.smallP.service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllCategoriesAsJson() {
        // Gọi phương thức của CategoryService để lấy danh sách danh mục dưới dạng JSON
        String categoryListJson = categoryService.getCategoryListAsJson();

        // Trả về chuỗi JSON
        return categoryListJson;
    }

}
