package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.service.factory.CustomerServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerServiceFactory customerServiceFactory;

    public CustomerController(CustomerServiceFactory customerServiceFactory) {
        this.customerServiceFactory = customerServiceFactory;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Validated @RequestBody CustomerDTO customer,
                                                      @RequestHeader("api-version") int apiVersion) {
        final var customerService = customerServiceFactory.getService(apiVersion);
        final var createdCustomer = customerService.createCustomer(customer);
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.id())
                .toUri();
        return ResponseEntity.created(location).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,
                                                      @Validated @RequestBody CustomerDTO customer,
                                                      @RequestHeader("api-version") int apiVersion) {
        final var customerService = customerServiceFactory.getService(apiVersion);
        final var updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id,
                                               @RequestHeader("api-version") int apiVersion) {
        final var customerService = customerServiceFactory.getService(apiVersion);
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id,
                                                   @RequestHeader("api-version") int apiVersion) {
        final var customerService = customerServiceFactory.getService(apiVersion);
        final var customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size,
                                                             @RequestHeader("api-version") int apiVersion) {
        final var customerService = customerServiceFactory.getService(apiVersion);
        final var customers = customerService.getAllCustomers(page, size);
        return ResponseEntity.ok(customers);
    }
}
