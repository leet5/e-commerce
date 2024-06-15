package com.leet5.ecommerce.exception.handlers;

import com.leet5.ecommerce.exception.CustomerCreationException;
import com.leet5.ecommerce.exception.CustomerNotFoundException;
import com.leet5.ecommerce.exception.CustomerUpdateException;
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
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        log.error("Customer not found error: {}", ex.getMessage());

        final Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Customer not found");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCustomerCreationException(CustomerCreationException ex) {
        log.error("Customer creation error: {}", ex.getMessage());

        final Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Customer creation error");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCustomerUpdateException(CustomerUpdateException ex) {
        log.error("Customer update error: {}", ex.getMessage());

        final Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Customer update error");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
