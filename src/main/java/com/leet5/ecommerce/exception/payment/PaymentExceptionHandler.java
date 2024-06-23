package com.leet5.ecommerce.exception.payment;

import com.leet5.ecommerce.exception.dto.NotFoundExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PaymentExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(PaymentExceptionHandler.class);

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePaymentNotFoundException(PaymentNotFoundException e) {
        log.error(e.getMessage());

        final var dto = new NotFoundExceptionDTO("Customer not found", e.getMessage());

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
