package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.OrderRequestDto;
import com.rehi.productservicedrehicapstone.dtos.OrderResponseDto;
import com.rehi.productservicedrehicapstone.dtos.OrderTrackingDto;
import com.rehi.productservicedrehicapstone.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    /**
     * POST /api/orders/checkout
     * Place an order for a user's current cart.
     */
    @PostMapping("/orders/checkout")
    public ResponseEntity<OrderResponseDto> checkout(@RequestBody OrderRequestDto orderRequestDto) {
        log.info("Placing order for userId={} with paymentMethod={}",
                orderRequestDto.getUserId(), orderRequestDto.getPaymentMethod());
        OrderResponseDto responseDto = orderService.checkout(orderRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * GET /api/users/{userId}/orders
     * Paginated order history for a user.
     */
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersForUser(
            @PathVariable("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("Fetching order history for userId={}, page={}, size={}", userId, page, size);
        Page<OrderResponseDto> orders = orderService.getOrdersByUser(userId, page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * GET /api/orders/{orderId}
     * Full order confirmation details.
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("orderId") Long orderId) {
        log.info("Fetching order details for orderId={}", orderId);
        OrderResponseDto responseDto = orderService.getOrderById(orderId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * GET /api/orders/{orderId}/track
     * Tracking information for an order.
     */
    @GetMapping("/orders/{orderId}/track")
    public ResponseEntity<OrderTrackingDto> trackOrder(@PathVariable("orderId") Long orderId) {
        log.info("Tracking order status for orderId={}", orderId);
        OrderTrackingDto trackingDto = orderService.trackOrderStatus(orderId);
        return new ResponseEntity<>(trackingDto, HttpStatus.OK);
    }
}

