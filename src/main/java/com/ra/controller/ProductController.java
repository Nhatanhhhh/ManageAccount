package com.ra.controller;

import com.ra.model.dto.productDTO.ProductRequestDTO;
import com.ra.model.dto.productDTO.ProductResponseDTO;
import com.ra.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Product", description = "Controller manage product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    @Operation(summary = "Get all products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", defaultValue = "asc") String orderBy
    ) {
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<ProductResponseDTO> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/admin/products/add")
    @Operation(summary = "Add new product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO productResponseDTO = productService.createProduct(requestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/products/edit/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Edit product with productId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, requestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/admin/products/edit/status/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Change status with productId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated",
                    content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductResponseDTO> changeProductStatus(@PathVariable Long id, @RequestParam Boolean status) {
        ProductResponseDTO productResponseDTO = productService.changeProductStatus(id, status);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }
}