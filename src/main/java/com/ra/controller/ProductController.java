package com.ra.controller;

import com.ra.model.dto.productDTO.ProductRequestDTO;
import com.ra.model.dto.productDTO.ProductResponseDTO;
import com.ra.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Thêm mới product (ADMIN only)
    @PostMapping("/admin/products/add")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO productResponseDTO = productService.createProduct(requestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/products/edit/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(id, requestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/admin/products/edit/status/{id}")
    public ResponseEntity<ProductResponseDTO> changeProductStatus(@PathVariable Long id, @RequestParam Boolean status) {
        ProductResponseDTO productResponseDTO = productService.changeProductStatus(id, status);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }
}