package com.rehi.productservicedrehicapstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    private Long orderId;
    private String paymentMethod;
    private Double amount;
}


