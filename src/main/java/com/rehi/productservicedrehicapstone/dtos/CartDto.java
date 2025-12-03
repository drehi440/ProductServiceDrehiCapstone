package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private Long cartId;
    private Double totalPrice;
    private List<CartItemDto> cartItems;

    public static CartDto from(Cart cart) {
        if (cart == null) {
            return null;
        }
        List<CartItemDto> items = cart.getCartItems()
                .stream()
                .map(CartItemDto::from)
                .collect(Collectors.toList());

        return CartDto.builder()
                .cartId(cart.getCartId())
                .totalPrice(cart.getTotalPrice())
                .cartItems(items)
                .build();
    }
}


