package com.ra.controller;

import com.ra.model.dto.categoryDTO.CategoryRequestDTO;
import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "View all category")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of categories",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "Invalid sortBy", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
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

    @Operation(summary = "Add new category")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO requestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(requestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Edit category with categoryId")
    @PutMapping("/edit/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated",
                    content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO requestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, requestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Edit status category with categoryId")
    @PatchMapping("/edit/status/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated",
                    content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> changeCategoryStatus(@PathVariable Long id, @RequestParam Boolean status) {
        try {
            CategoryResponseDTO responseDTO = categoryService.changeCategoryStatus(id, status);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
