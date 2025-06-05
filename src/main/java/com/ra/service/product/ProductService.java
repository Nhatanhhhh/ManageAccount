package com.ra.service.product;

import com.ra.model.dto.productDTO.ProductRequestDTO;
import com.ra.model.dto.productDTO.ProductResponseDTO;
import com.ra.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);
    ProductResponseDTO changeProductStatus(Long id, Boolean status);
    ProductResponseDTO convertToResponseDTO(Product product);
}
