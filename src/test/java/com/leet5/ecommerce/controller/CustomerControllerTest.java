package com.leet5.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leet5.ecommerce.exception.customer.CustomerNotFoundException;
import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.service.CustomerService;
import com.leet5.ecommerce.service.factory.CustomerServiceFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerServiceFactory customerServiceFactory;

    @Test
    public void createCustomer_success() throws Exception {
        final var newCustomer = new CustomerDTO(null, "John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), List.of());

        final var service = Mockito.mock(CustomerService.class);
        when(service.createCustomer(any(CustomerDTO.class))).thenReturn(newCustomer);
        when(customerServiceFactory.getService(1)).thenReturn(service);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/customers")
                        .header("api-version", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void deleteCustomer_success() throws Exception {
        var customerId = 1L;
        var service = Mockito.mock(CustomerService.class);
        when(customerServiceFactory.getService(1)).thenReturn(service);


        mockMvc.perform(delete("/customers/{id}", customerId)
                        .header("api-version", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomer_notFound() throws Exception {
        var customerId = 1L;
        var service = Mockito.mock(CustomerService.class);
        doThrow(new CustomerNotFoundException("Customer with id " + customerId + " not found")).when(service).deleteCustomer(customerId);
        when(customerServiceFactory.getService(1)).thenReturn(service);

        mockMvc.perform(delete("/customers/{id}", customerId)
                        .header("api-version", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCustomers_success() throws Exception {
        final var customer1 = new CustomerDTO(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(1995, 8, 1), List.of());
        final var customer2 = new CustomerDTO(2L, "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 8, 1), List.of());
        final var customers = List.of(customer1, customer2);

        final var service = Mockito.mock(CustomerService.class);
        when(service.getAllCustomers(0, 20)).thenReturn(customers);
        when(customerServiceFactory.getService(1)).thenReturn(service);

        mockMvc.perform(get("/customers")
                        .header("api-version", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\"},{\"id\":2,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.com\"}]"));
    }

    @Test
    void updateCustomer_success() throws Exception {
        final var customer = new CustomerDTO(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(1995, 8, 1), List.of());
        final var service = Mockito.mock(CustomerService.class);

        when(service.updateCustomer(any(), any(CustomerDTO.class))).thenReturn(customer);
        when(customerServiceFactory.getService(1)).thenReturn(service);

        mockMvc.perform(put("/customers/{id}", 1L)
                        .header("api-version", 1)
                        .content(objectMapper.writeValueAsString(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }
}
