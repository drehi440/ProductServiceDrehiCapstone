package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Order;
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
public class OrderResponseDto {

    private Long orderId;
    private String status;
    private Double totalAmount;
    private List<OrderItemDto> orderItems;
    private PaymentDto paymentDetails;

    public static OrderResponseDto from(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItemDto> itemDtos = order.getOrderItems()
                .stream()
                .map(OrderItemDto::from)
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .status(order.getPurchaseStatus())
                .totalAmount(order.getTotalAmount())
                .orderItems(itemDtos)
                // Payment is now looked up separately via PaymentRepository; not mapped on the entity.
                .paymentDetails(null)
                .build();
    }
}


