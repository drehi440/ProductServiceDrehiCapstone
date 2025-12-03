package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.OrderRequestDto;
import com.rehi.productservicedrehicapstone.dtos.OrderResponseDto;

public interface OrderService {

    OrderResponseDto checkout(OrderRequestDto orderRequestDto);
}


