package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.CartDto;
import com.rehi.productservicedrehicapstone.dtos.CartItemRequestDto;
import com.rehi.productservicedrehicapstone.exceptions.ResourceNotFoundException;
import com.rehi.productservicedrehicapstone.models.Cart;
import com.rehi.productservicedrehicapstone.models.CartItem;
import com.rehi.productservicedrehicapstone.models.Product;
import com.rehi.productservicedrehicapstone.repositories.CartItemRepository;
import com.rehi.productservicedrehicapstone.repositories.CartRepository;
import com.rehi.productservicedrehicapstone.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CartDto addItemToCart(Long userId, CartItemRequestDto requestDto) {
        // Find or create cart for user.
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .userId(userId)
                            .totalPrice(0.0)
                            .build();
                    return cartRepository.save(newCart);
                });

        // Ensure product exists.
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id " + requestDto.getProductId()));

        // Determine price snapshot (prefer specialPrice if exists).
        Double effectivePrice = product.getSpecialPrice() != null
                ? product.getSpecialPrice()
                : product.getPrice();

        // Discount can capture any promotional discount at the time.
        Double discount = product.getDiscount() != null ? product.getDiscount() : 0.0;

        // Check if item already exists in cart.
        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(ci -> ci.getProductId().equals(requestDto.getProductId()))
                .findFirst();

        CartItem item;
        if (existingItemOpt.isPresent()) {
            item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + requestDto.getQuantity());
        } else {
            item = CartItem.builder()
                    .productId(requestDto.getProductId())
                    .quantity(requestDto.getQuantity())
                    .discount(discount)
                    .productPrice(effectivePrice)
                    .cart(cart)
                    .build();
            cart.getCartItems().add(item);
        }

        // Recalculate cart total as sum of (price * quantity).
        double total = cart.getCartItems().stream()
                .mapToDouble(ci -> ci.getProductPrice() * ci.getQuantity())
                .sum();
        cart.setTotalPrice(total);

        Cart savedCart = cartRepository.save(cart);
        log.info("Updated cart for userId={}, totalPrice={}", userId, savedCart.getTotalPrice());

        return CartDto.from(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartDto getCartForUser(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for userId " + userId));
        return CartDto.from(cart);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long userId, Long cartItemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for userId " + userId));

        boolean removed = cart.getCartItems().removeIf(item -> item.getCartItemId().equals(cartItemId));
        if (!removed) {
            throw new ResourceNotFoundException("Cart item " + cartItemId + " not found in user's cart");
        }

        // Recalculate total or clear cart if empty.
        double total = cart.getCartItems().stream()
                .mapToDouble(ci -> ci.getProductPrice() * ci.getQuantity())
                .sum();
        cart.setTotalPrice(total);

        cartRepository.save(cart);
        log.info("Removed item {} from cart for userId={}, newTotal={}", cartItemId, userId, total);
    }
}


