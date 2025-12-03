package com.rehi.productservicedrehicapstone.exceptions;

import com.rehi.productservicedrehicapstone.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDto> handleNullPointerExceptions()
    {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage("NullPointer exception occurred");

        ResponseEntity<ErrorDto> responseEntity = 
        new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;

        //  return errorDto;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(
            ProductNotFoundException productNotFoundException)
    {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage(productNotFoundException.getMessage());

        // return errorDto;
        ResponseEntity<ErrorDto> responseEntity = 
        new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException)
    {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage(resourceNotFoundException.getMessage());

        ResponseEntity<ErrorDto> responseEntity =
                new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(
            UserNotFoundException userNotFoundException)
    {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage(userNotFoundException.getMessage());

        ResponseEntity<ErrorDto> responseEntity =
                new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDto> handleInvalidCredentialsException(
            InvalidCredentialsException invalidCredentialsException)
    {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage(invalidCredentialsException.getMessage());

        ResponseEntity<ErrorDto> responseEntity =
                new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
        return responseEntity;
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorDto> handlePaymentFailedException(
            PaymentFailedException paymentFailedException)
    {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setStatus("Failure");
        errorDto.setMessage(paymentFailedException.getMessage());

        ResponseEntity<ErrorDto> responseEntity =
                new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}