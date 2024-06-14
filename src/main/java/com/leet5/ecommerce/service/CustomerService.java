package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    void deleteCustomer(Long id);
}