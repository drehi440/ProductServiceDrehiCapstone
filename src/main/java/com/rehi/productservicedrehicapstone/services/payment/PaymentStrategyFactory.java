package com.rehi.productservicedrehicapstone.services.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {

    private final UpiPaymentStrategy upiPaymentStrategy;
    private final CardPaymentStrategy cardPaymentStrategy;
    private final NetBankingStrategy netBankingStrategy;

    public PaymentStrategyFactory(UpiPaymentStrategy upiPaymentStrategy,
                                  CardPaymentStrategy cardPaymentStrategy,
                                  NetBankingStrategy netBankingStrategy) {
        this.upiPaymentStrategy = upiPaymentStrategy;
        this.cardPaymentStrategy = cardPaymentStrategy;
        this.netBankingStrategy = netBankingStrategy;
    }

    public PaymentStrategy getStrategy(String paymentMethod) {
        if (paymentMethod == null) {
            return cardPaymentStrategy;
        }
        String normalized = paymentMethod.trim().toUpperCase();
        return switch (normalized) {
            case "UPI" -> upiPaymentStrategy;
            case "NET_BANKING", "NETBANKING" -> netBankingStrategy;
            case "CREDIT_CARD", "DEBIT_CARD", "CARD" -> cardPaymentStrategy;
            default -> cardPaymentStrategy;
        };
    }
}


