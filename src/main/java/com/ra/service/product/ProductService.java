package com.ra.service.product;

import com.ra.model.dto.product.ProductRequestDTO;
import com.ra.model.dto.product.ProductResponseDTO;
import com.ra.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    Product findById(Long id);
    ProductResponseDTO save(ProductRequestDTO productDTO);
    void delete(Long id);
}
