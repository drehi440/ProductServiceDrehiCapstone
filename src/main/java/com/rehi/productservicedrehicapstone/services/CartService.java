package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.CartDto;
import com.rehi.productservicedrehicapstone.dtos.CartItemRequestDto;

public interface CartService {

    CartDto addItemToCart(Long userId, CartItemRequestDto requestDto);

    CartDto getCartForUser(Long userId);

    void removeItemFromCart(Long userId, Long cartItemId);
}


