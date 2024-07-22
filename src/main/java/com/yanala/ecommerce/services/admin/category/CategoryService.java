package com.yanala.ecommerce.services.admin.category;

import com.yanala.ecommerce.dto.CategoryDto;
import com.yanala.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}
