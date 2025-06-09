package com.ra.service.category;

import com.ra.model.entity.Category;
import com.ra.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.model.dto.categoryDTO.CategoryRequestDTO;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        if (categoryRepository.existsByName(requestDTO.getName())) {
            throw new RuntimeException("Category name already exists");
        }
        Category category = new Category();
        category.setName(requestDTO.getName());
        category.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : true);
        Category savedCategory = categoryRepository.save(category);
        return convertToResponseDTO(savedCategory);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        if (!category.getName().equals(requestDTO.getName()) && categoryRepository.existsByName(requestDTO.getName())) {
            throw new RuntimeException("Category name already exists");
        }
        category.setName(requestDTO.getName());
        if (requestDTO.getStatus() != null) {
            category.setStatus(requestDTO.getStatus());
        }
        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDTO(updatedCategory);
    }

    @Override
    public Page<CategoryResponseDTO> searchCategoriesByName(String searchText, Pageable pageable) {
        return categoryRepository.findBySearchText(searchText, pageable)
                .map(this::convertToResponseDTO);
    }

    @Override
    public Page<CategoryResponseDTO> filterCategoriesByStatus(Boolean status, Pageable pageable) {
        return categoryRepository.findByStatus(status, pageable)
                .map(this::convertToResponseDTO);
    }

    @Override
    public Page<CategoryResponseDTO> searchAndFilterCategories(String searchText, Boolean status, Pageable pageable) {
        return categoryRepository.findBySearchTextAndStatus(searchText, status, pageable)
                .map(this::convertToResponseDTO);
    }

    @Override
    public void validateSortBy(String sortBy) {
        List<String> validFields = Arrays.asList("id", "name", "status");
        if (!validFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
        }
    }

    @Override
    public CategoryResponseDTO changeCategoryStatus(Long id, Boolean status) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        category.setStatus(status);
        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDTO(updatedCategory);
    }

    @Override
    public CategoryResponseDTO convertToResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .build();
    }
}
