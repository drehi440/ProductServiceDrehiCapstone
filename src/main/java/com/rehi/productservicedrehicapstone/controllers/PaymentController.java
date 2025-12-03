package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.PaymentRequestDto;
import com.rehi.productservicedrehicapstone.dtos.PaymentResponseDto;
import com.rehi.productservicedrehicapstone.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    /**
     * POST /api/payments/process
     * Initiates payment for a given order and returns a payment receipt.
     */
    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestBody PaymentRequestDto requestDto) {
        log.info("Processing payment for orderId={} using method={}",
                requestDto.getOrderId(), requestDto.getPaymentMethod());
        PaymentResponseDto responseDto = paymentService.initiatePayment(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}


