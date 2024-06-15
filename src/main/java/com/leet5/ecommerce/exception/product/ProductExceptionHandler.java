package com.leet5.ecommerce.exception.product;

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
public class ProductExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ProductExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e) {
        log.error("Product not found: {}", e.getMessage());

        final Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Product not found");
        errorResponse.put("message", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
