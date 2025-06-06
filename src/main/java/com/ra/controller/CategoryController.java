package com.ra.controller;

import com.ra.model.dto.categoryDTO.CategoryRequestDTO;
import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", defaultValue = "asc") String orderBy,
            @RequestParam(name = "searchText", required = false) String searchText,
            @RequestParam(name = "status", required = false) Boolean status
    ) {
        categoryService.validateSortBy(sortBy);

        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<CategoryResponseDTO> categories;
        if (searchText != null && !searchText.isEmpty() && status != null) {
            categories = categoryService.searchAndFilterCategories(searchText, status, pageable);
        } else if (searchText != null && !searchText.isEmpty()) {
            categories = categoryService.searchCategoriesByName(searchText, pageable);
        } else if (status != null) {
            categories = categoryService.filterCategoriesByStatus(status, pageable);
        } else {
            categories = categoryService.getAllCategories(pageable);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categories.getContent());
        response.put("currentPage", categories.getNumber());
        response.put("totalItems", categories.getTotalElements());
        response.put("totalPages", categories.getTotalPages());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO requestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(requestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO requestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, requestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/edit/status/{id}")
    public ResponseEntity<CategoryResponseDTO> changeCategoryStatus(@PathVariable Long id, @RequestParam Boolean status) {
        try {
            CategoryResponseDTO responseDTO = categoryService.changeCategoryStatus(id, status);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
