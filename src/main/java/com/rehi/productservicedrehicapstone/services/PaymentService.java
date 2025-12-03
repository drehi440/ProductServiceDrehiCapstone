package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.PaymentRequestDto;
import com.rehi.productservicedrehicapstone.dtos.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto initiatePayment(PaymentRequestDto requestDto);
}


