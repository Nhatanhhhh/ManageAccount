package com.ra.service.product;

import com.ra.model.dto.productDTO.ProductRequestDTO;
import com.ra.model.dto.productDTO.ProductResponseDTO;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductResponseDTO> getAllProducts(Pageable pageable);
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);
    ProductResponseDTO changeProductStatus(Long id, Boolean status);
    ProductResponseDTO convertToResponseDTO(Product product);
}
