package com.rehi.productservicedrehicapstone.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    /**
     * Logical link to the order this payment belongs to.
     */
    private Long orderId;

    private String paymentMethod;

    private String transactionStatus;

    /**
     * Mock URL representing a hosted payment page.
     */
    private String paymentLink;

    private Double totalAmount;

    /**
     * Reference (UUID) provided by the payment system.
     */
    private String transactionReference;
}


