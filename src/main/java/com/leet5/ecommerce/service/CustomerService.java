package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(Customer customer);

    CustomerDTO updateCustomer(Long id, Customer customer);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    void deleteCustomer(Long id);
}