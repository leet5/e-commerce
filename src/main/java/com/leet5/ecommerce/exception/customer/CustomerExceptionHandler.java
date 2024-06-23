package com.leet5.ecommerce.exception.customer;

import com.leet5.ecommerce.exception.dto.NotFoundExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomerExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException e) {
        log.error("Customer not found error: {}", e.getMessage());

        final var dto = new NotFoundExceptionDTO("Customer not found", e.getMessage());

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCustomerCreationException(CustomerCreationException e) {
        log.error("Customer creation error: {}", e.getMessage());

        final Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Customer creation error");
        errorResponse.put("message", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCustomerUpdateException(CustomerUpdateException e) {
        log.error("Customer update error: {}", e.getMessage());

        final Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Customer update error");
        errorResponse.put("message", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
