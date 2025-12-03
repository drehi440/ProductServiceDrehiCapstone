package com.rehi.productservicedrehicapstone.services.payment;

public interface PaymentStrategy {

    /**
     * Mock payment processing. Returns true if the payment is considered successful.
     */
    boolean processPayment(Double amount);
}


