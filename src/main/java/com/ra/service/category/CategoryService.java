package com.ra.service.category;

import com.ra.model.dto.categoryDTO.CategoryRequestDTO;
import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);
    CategoryResponseDTO changeCategoryStatus(Long id, Boolean status);
    CategoryResponseDTO convertToResponseDTO(Category category);
}
