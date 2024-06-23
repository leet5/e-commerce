package com.leet5.ecommerce.exception.order;

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
public class OrderExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(OrderExceptionHandler.class);

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> orderNotFoundException(OrderNotFoundException e) {
        log.error(e.getMessage());

        final var dto = new NotFoundExceptionDTO("Order not found", e.getMessage());

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> orderUpdateException(OrderUpdateException e) {
        log.error(e.getMessage());

        final Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error", "Order update error");
        errorResponse.put("message", e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
