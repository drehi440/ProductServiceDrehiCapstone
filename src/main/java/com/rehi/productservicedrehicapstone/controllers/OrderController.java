package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.OrderRequestDto;
import com.rehi.productservicedrehicapstone.dtos.OrderResponseDto;
import com.rehi.productservicedrehicapstone.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    /**
     * POST /api/orders/checkout
     * Place an order for a user's current cart.
     */
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDto> checkout(@RequestBody OrderRequestDto orderRequestDto) {
        log.info("Placing order for userId={} with paymentMethod={}",
                orderRequestDto.getUserId(), orderRequestDto.getPaymentMethod());
        OrderResponseDto responseDto = orderService.checkout(orderRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}


