package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long paymentId;
    private String status;
    private String transactionReference;
    private Double amount;
    private LocalDateTime timestamp;

    public static PaymentResponseDto from(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getPaymentId())
                .status(payment.getTransactionStatus())
                .transactionReference(payment.getTransactionReference())
                .amount(payment.getTotalAmount())
                .timestamp(LocalDateTime.now())
                .build();
    }
}


