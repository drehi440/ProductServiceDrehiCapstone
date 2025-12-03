package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTrackingDto {

    private Long orderId;
    private String status;
    private LocalDate orderDate;
    private LocalDate estimatedDelivery;

    public static OrderTrackingDto from(Order order, LocalDate estimatedDelivery) {
        return OrderTrackingDto.builder()
                .orderId(order.getOrderId())
                .status(order.getPurchaseStatus())
                .orderDate(order.getOrderDate())
                .estimatedDelivery(estimatedDelivery)
                .build();
    }
}


