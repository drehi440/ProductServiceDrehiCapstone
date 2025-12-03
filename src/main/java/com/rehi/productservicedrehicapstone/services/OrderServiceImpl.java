package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.OrderRequestDto;
import com.rehi.productservicedrehicapstone.dtos.OrderResponseDto;
import com.rehi.productservicedrehicapstone.exceptions.ResourceNotFoundException;
import com.rehi.productservicedrehicapstone.models.*;
import com.rehi.productservicedrehicapstone.repositories.CartRepository;
import com.rehi.productservicedrehicapstone.repositories.OrderRepository;
import com.rehi.productservicedrehicapstone.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponseDto checkout(OrderRequestDto orderRequestDto) {
        Long userId = orderRequestDto.getUserId();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for userId " + userId));

        if (cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty for userId " + userId);
        }

        // Create order from cart snapshot.
        Order order = Order.builder()
                .userId(userId)
                .orderDate(LocalDate.now())
                .purchaseStatus("AWAITING_CONFIRMATION")
                .build();

        double totalAmount = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {
            // Optional stock check: if product has a quantity configured, ensure it's sufficient.
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id " + cartItem.getProductId()));

            if (product.getQuantity() != null && product.getQuantity() < cartItem.getQuantity()) {
                throw new ResourceNotFoundException(
                        "Insufficient stock for product id " + cartItem.getProductId());
            }

            OrderItem orderItem = OrderItem.builder()
                    .productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity())
                    .discount(cartItem.getDiscount())
                    .orderedProductPrice(cartItem.getProductPrice())
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);

            totalAmount += cartItem.getProductPrice() * cartItem.getQuantity();

            // Reduce stock if quantity is being tracked.
            if (product.getQuantity() != null) {
                product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                productRepository.save(product);
            }
        }

        order.setTotalAmount(totalAmount);

        // Mock payment: mark as SUCCESS.
        Payment payment = Payment.builder()
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .transactionStatus("SUCCESS")
                .totalAmount(totalAmount)
                .order(order)
                .build();

        order.setPayment(payment);
        order.setPurchaseStatus("COMPLETED");

        Order savedOrder = orderRepository.save(order);

        // Cleanup cart after successful checkout.
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        log.info("Checkout completed for userId={}, orderId={}, totalAmount={}",
                userId, savedOrder.getOrderId(), totalAmount);

        return OrderResponseDto.from(savedOrder);
    }
}


