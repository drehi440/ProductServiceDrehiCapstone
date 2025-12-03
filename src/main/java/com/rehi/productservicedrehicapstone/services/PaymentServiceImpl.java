package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.PaymentRequestDto;
import com.rehi.productservicedrehicapstone.dtos.PaymentResponseDto;
import com.rehi.productservicedrehicapstone.exceptions.PaymentFailedException;
import com.rehi.productservicedrehicapstone.exceptions.ResourceNotFoundException;
import com.rehi.productservicedrehicapstone.models.Order;
import com.rehi.productservicedrehicapstone.models.Payment;
import com.rehi.productservicedrehicapstone.repositories.OrderRepository;
import com.rehi.productservicedrehicapstone.repositories.PaymentRepository;
import com.rehi.productservicedrehicapstone.services.payment.PaymentStrategy;
import com.rehi.productservicedrehicapstone.services.payment.PaymentStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;

    @Override
    @Transactional
    public PaymentResponseDto initiatePayment(PaymentRequestDto requestDto) {
        Long orderId = requestDto.getOrderId();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));

        // Check if order is already paid
        Optional<Payment> existingPaymentOpt = paymentRepository.findByOrderId(orderId);
        if (existingPaymentOpt.isPresent()
                && "SUCCESS".equalsIgnoreCase(existingPaymentOpt.get().getTransactionStatus())) {
            throw new PaymentFailedException("Order " + orderId + " is already paid");
        }

        Double expectedAmount = order.getTotalAmount();
        if (expectedAmount == null || !expectedAmount.equals(requestDto.getAmount())) {
            throw new PaymentFailedException("Payment amount does not match order total");
        }

        PaymentStrategy strategy = paymentStrategyFactory.getStrategy(requestDto.getPaymentMethod());
        boolean success = strategy.processPayment(requestDto.getAmount());

        String transactionRef = UUID.randomUUID().toString();
        String paymentLink = "https://mock-payments.example.com/pay/" + transactionRef;

        Payment payment = Payment.builder()
                .orderId(orderId)
                .paymentMethod(requestDto.getPaymentMethod())
                .totalAmount(requestDto.getAmount())
                .paymentLink(paymentLink)
                .transactionReference(transactionRef)
                .transactionStatus(success ? "SUCCESS" : "FAILED")
                .build();

        payment = paymentRepository.save(payment);

        if (success) {
            order.setPurchaseStatus("CONFIRMED");
            orderRepository.save(order);
            log.info("Payment SUCCESS for orderId={}, paymentId={}", orderId, payment.getPaymentId());
            return PaymentResponseDto.from(payment);
        } else {
            log.warn("Payment FAILED for orderId={}, paymentId={}", orderId, payment.getPaymentId());
            throw new PaymentFailedException("Payment processing failed for order " + orderId);
        }
    }
}


