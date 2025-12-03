package com.rehi.productservicedrehicapstone.services.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("netBankingStrategy")
public class NetBankingStrategy implements PaymentStrategy {

    private static final Logger log = LoggerFactory.getLogger(NetBankingStrategy.class);

    @Override
    public boolean processPayment(Double amount) {
        // Mock Net Banking processing
        log.info("Processing NetBanking payment for amount={}", amount);
        return true;
    }
}


