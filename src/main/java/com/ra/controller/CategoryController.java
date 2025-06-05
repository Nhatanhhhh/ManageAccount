package com.ra.controller;

import com.ra.model.dto.categoryDTO.CategoryRequestDTO;
import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categories/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
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
