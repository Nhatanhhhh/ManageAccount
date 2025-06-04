package com.ra.model.dto.product;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequestDTO {
    private String productName;
    private Double price;
    private MultipartFile image;
    private Boolean status;
    private Long categoryId;
}
