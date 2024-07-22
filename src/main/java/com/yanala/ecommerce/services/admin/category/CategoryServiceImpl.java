package com.yanala.ecommerce.services.admin.category;

import com.yanala.ecommerce.dto.CategoryDto;
import com.yanala.ecommerce.entity.Category;
import com.yanala.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
