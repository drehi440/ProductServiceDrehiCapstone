package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long paymentId;
    private String paymentMethod;
    private String transactionStatus;
    private Double totalAmount;

    public static PaymentDto from(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .paymentMethod(payment.getPaymentMethod())
                .transactionStatus(payment.getTransactionStatus())
                .totalAmount(payment.getTotalAmount())
                .build();
    }
}


