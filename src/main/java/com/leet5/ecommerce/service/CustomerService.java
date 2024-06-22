package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customer);

    CustomerDTO updateCustomer(Long id, CustomerDTO customer);

    List<CustomerDTO> getAllCustomers(int page, int size);

    CustomerDTO getCustomerById(Long id);

    void deleteCustomer(Long id);

    int getVersion();
}