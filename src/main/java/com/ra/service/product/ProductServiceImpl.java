package com.ra.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra.model.dto.product.ProductResponseDTO;
import com.ra.model.entity.Product;
import com.ra.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        // covert Entity = DTO
        List<ProductResponseDTO> responseDTOS;

        responseDTOS = products.stream().map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .image(product.getImage())
                .status(product.getStatus())
                .categoryName(product.getCategory().getCategoryName())
                .build()
        ).collect(Collectors.toList());
        return responseDTOS;
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
