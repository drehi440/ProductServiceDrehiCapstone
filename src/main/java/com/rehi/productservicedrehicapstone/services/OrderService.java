package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.OrderRequestDto;
import com.rehi.productservicedrehicapstone.dtos.OrderResponseDto;
import com.rehi.productservicedrehicapstone.dtos.OrderTrackingDto;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponseDto checkout(OrderRequestDto orderRequestDto);

    Page<OrderResponseDto> getOrdersByUser(Long userId, int page, int size);

    OrderResponseDto getOrderById(Long orderId);

    OrderTrackingDto trackOrderStatus(Long orderId);
}


