package com.ra.service.product;

import com.ra.model.dto.product.ProductResponseDTO;
import com.ra.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    Product findById(Long id);
    Product save(Product product);
    void delete(Long id);
}
