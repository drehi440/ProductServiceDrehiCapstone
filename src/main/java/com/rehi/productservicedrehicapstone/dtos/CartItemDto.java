package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private Long cartItemId;
    private Long productId;
    private Integer quantity;
    private Double discount;
    private Double productPrice;

    public static CartItemDto from(CartItem item) {
        if (item == null) {
            return null;
        }
        return CartItemDto.builder()
                .cartItemId(item.getCartItemId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .discount(item.getDiscount())
                .productPrice(item.getProductPrice())
                .build();
    }
}


