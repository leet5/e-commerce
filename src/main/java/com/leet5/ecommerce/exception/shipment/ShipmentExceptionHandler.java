package com.leet5.ecommerce.exception.shipment;

import com.leet5.ecommerce.exception.dto.NotFoundExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ShipmentExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ShipmentExceptionHandler.class);

    @ExceptionHandler(ShipmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleShipmentNotFoundException(ShipmentNotFoundException e) {
        log.error(e.getMessage());

        final var dto = new NotFoundExceptionDTO("Shipment not found", e.getMessage());

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
