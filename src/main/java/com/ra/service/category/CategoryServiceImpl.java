package com.ra.service.category;

import com.ra.model.entity.Category;
import com.ra.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.model.dto.categoryDTO.CategoryRequestDTO;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Hiển thị tất cả category
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Thêm mới category
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

    // Sửa thông tin category
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

    // Thay đổi trạng thái category
    public CategoryResponseDTO changeCategoryStatus(Long id, Boolean status) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        category.setStatus(status);
        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDTO(updatedCategory);
    }

    public CategoryResponseDTO convertToResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .build();
    }
}
