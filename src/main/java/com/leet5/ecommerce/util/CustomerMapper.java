package com.leet5.ecommerce.util;

import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.model.entity.Order;

public class CustomerMapper {
    public static CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getBirthdate(),
                customer.getOrders().stream().map(Order::getId).toList()
        );
    }

    public static Customer toCustomer(CustomerDTO customerDTO) {
        final var customer = new Customer();
        customer.setFirstName(customerDTO.firstName());
        customer.setLastName(customerDTO.lastName());
        customer.setEmail(customerDTO.email());
        customer.setBirthdate(customerDTO.birthdate());
        return customer;
    }
}
