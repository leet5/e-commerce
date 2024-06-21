package com.leet5.ecommerce.service.implementation;

import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerServiceV2Impl implements CustomerService {
    private static final int VERSION = 2;

    @Override
    public CustomerDTO createCustomer(Customer customer) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomer(Long id, Customer customer) {
        return null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers(int page, int size) {
        return List.of();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {

    }

    @Override
    public int getVersion() {
        return VERSION;
    }
}
