package com.example.smallP.service.Category;

import com.example.smallP.entity.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String getCategoryListAsJson() {
        List<Category> categoryList = categoryRepository.findAll();

        // Tạo đối tượng JSON
        JsonObject jsonObject = new JsonObject();

        // Tạo mảng JSON và thêm dữ liệu từ danh sách
        JsonArray dataArray = new JsonArray();
        categoryList.forEach(category -> dataArray.add(category.getCategory()));

        jsonObject.add("data", dataArray);

        // Chuyển đối tượng JSON thành chuỗi JSON và trả về
        return jsonObject.toString();
    }
}

