package com.rehi.productservicedrehicapstone.services.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("upiPaymentStrategy")
public class UpiPaymentStrategy implements PaymentStrategy {

    private static final Logger log = LoggerFactory.getLogger(UpiPaymentStrategy.class);

    @Override
    public boolean processPayment(Double amount) {
        // Mock UPI processing – in real implementation, call external API.
        log.info("Processing UPI payment for amount={}", amount);
        return true;
    }
}


