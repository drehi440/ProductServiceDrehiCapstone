package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long productId;
    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private String categoryName;

    /**
     * Static mapper method to convert a Product entity into a DTO.
     * Using DTOs avoids exposing JPA internals directly over the wire.
     */
    public static ProductResponseDto from(Product product) {
        if (product == null) {
            return null;
        }

        String resolvedCategoryName = product.getCategory() != null
                ? product.getCategory().getCategoryName()
                : null;

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .image(product.getImage())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .specialPrice(product.getSpecialPrice())
                .categoryName(resolvedCategoryName)
                .build();
    }
}

