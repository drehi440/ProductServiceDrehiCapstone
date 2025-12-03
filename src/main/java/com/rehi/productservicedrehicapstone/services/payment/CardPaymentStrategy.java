package com.rehi.productservicedrehicapstone.services.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cardPaymentStrategy")
public class CardPaymentStrategy implements PaymentStrategy {

    private static final Logger log = LoggerFactory.getLogger(CardPaymentStrategy.class);

    @Override
    public boolean processPayment(Double amount) {
        // Mock Card processing
        log.info("Processing Card payment for amount={}", amount);
        return true;
    }
}


