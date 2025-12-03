package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.CartDto;
import com.rehi.productservicedrehicapstone.dtos.CartItemRequestDto;
import com.rehi.productservicedrehicapstone.services.CartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    /**
     * POST /api/carts/{userId}/items
     * Add or update an item in the user's cart.
     */
    @PostMapping("/{userId}/items")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable("userId") Long userId,
                                                 @RequestBody CartItemRequestDto requestDto) {
        log.info("Adding item productId={} quantity={} to cart for userId={}",
                requestDto.getProductId(), requestDto.getQuantity(), userId);
        CartDto cartDto = cartService.addItemToCart(userId, requestDto);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    /**
     * GET /api/carts/{userId}
     * View the current state of the user's cart.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable("userId") Long userId) {
        log.info("Fetching cart for userId={}", userId);
        CartDto cartDto = cartService.getCartForUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    /**
     * DELETE /api/carts/{userId}/items/{itemId}
     * Remove a specific item from the user's cart.
     */
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable("userId") Long userId,
                                           @PathVariable("itemId") Long itemId) {
        log.info("Removing cartItemId={} from cart for userId={}", itemId, userId);
        cartService.removeItemFromCart(userId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


