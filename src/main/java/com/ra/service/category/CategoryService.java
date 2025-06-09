package com.ra.service.category;

import com.ra.model.dto.categoryDTO.CategoryRequestDTO;
import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponseDTO> getAllCategories(Pageable pageable);Page<CategoryResponseDTO> searchCategoriesByName(String searchText, Pageable pageable);
    Page<CategoryResponseDTO> filterCategoriesByStatus(Boolean status, Pageable pageable);
    Page<CategoryResponseDTO> searchAndFilterCategories(String searchText, Boolean status, Pageable pageable);
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);
    CategoryResponseDTO changeCategoryStatus(Long id, Boolean status);
    CategoryResponseDTO convertToResponseDTO(Category category);
    void validateSortBy(String sortBy);
}
