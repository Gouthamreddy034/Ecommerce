package com.yanala.ecommerce.services.admin.category;

import com.yanala.ecommerce.dto.CategoryDto;
import com.yanala.ecommerce.entity.Category;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
}
