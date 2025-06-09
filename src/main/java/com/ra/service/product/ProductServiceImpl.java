package com.ra.service.product;

import com.ra.model.dto.productDTO.ProductRequestDTO;
import com.ra.model.dto.productDTO.ProductResponseDTO;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.repository.CategoryRepository;
import com.ra.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + requestDTO.getCategoryId()));
        Product product = new Product();
        product.setName(requestDTO.getName());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setCategory(category);
        product.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : true);
        Product savedProduct = productRepository.save(product);
        return convertToResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + requestDTO.getCategoryId()));
        product.setName(requestDTO.getName());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setCategory(category);
        if (requestDTO.getStatus() != null) {
            product.setStatus(requestDTO.getStatus());
        }
        Product updatedProduct = productRepository.save(product);
        return convertToResponseDTO(updatedProduct);
    }

    @Override
    public ProductResponseDTO changeProductStatus(Long id, Boolean status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setStatus(status);
        Product updatedProduct = productRepository.save(product);
        return convertToResponseDTO(updatedProduct);
    }

    @Override
    public ProductResponseDTO convertToResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .status(product.getStatus())
                .build();
    }
}
