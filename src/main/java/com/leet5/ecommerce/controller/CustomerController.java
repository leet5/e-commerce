package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.exception.dto.NotFoundExceptionDTO;
import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.leet5.ecommerce.util.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/customers")
@Tag(name = "Customer", description = "Endpoints for managing customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Validated @RequestBody Customer customer) {
        final CustomerDTO createdCustomer = customerService.createCustomer(customer);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCustomer.id())
                .toUri();
        return ResponseEntity.created(location).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer) {
        final CustomerDTO updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a customer by ID", description = "Returns a single customer")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the customer",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundExceptionDTO.class))}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        final CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        final List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
