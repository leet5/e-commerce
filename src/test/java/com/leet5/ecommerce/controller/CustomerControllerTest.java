package com.leet5.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leet5.ecommerce.exception.customer.CustomerNotFoundException;
import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static com.leet5.ecommerce.util.ApiConstants.API_VERSION_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private CustomerService customerService;

    @Test
    public void createCustomer_success() throws Exception {
        final CustomerDTO newCustomer = new CustomerDTO(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), List.of());

        when(customerService.createCustomer(any(Customer.class))).thenReturn(newCustomer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(API_VERSION_1 + "/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
                        .value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
                        .value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value("john.doe@example.com"));
    }

    @Test
    void deleteCustomer_success() throws Exception {
        Long customerId = 1L;

        mockMvc.perform(delete(API_VERSION_1 + "/customers/{id}", customerId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomer_notFound() throws Exception {
        Long customerId = 1L;
        doThrow(new CustomerNotFoundException("Customer with id " + customerId + " not found")).when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete(API_VERSION_1 + "/customers/{id}", customerId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void getAllCustomers_success() throws Exception {
        CustomerDTO customer1 = new CustomerDTO(1L, "John", "Doe", "john.doe@example.com", LocalDate.of(1995, 8, 1), List.of());
        CustomerDTO customer2 = new CustomerDTO(2L, "Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 8, 1), List.of());

        final var customers = List.of(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(API_VERSION_1 + "/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\"},{\"id\":2,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.com\"}]"));
    }
}
