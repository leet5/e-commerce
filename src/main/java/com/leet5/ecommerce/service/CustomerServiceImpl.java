package com.leet5.ecommerce.service;

import com.leet5.ecommerce.exception.customer.CustomerCreationException;
import com.leet5.ecommerce.exception.customer.CustomerNotFoundException;
import com.leet5.ecommerce.exception.customer.CustomerUpdateException;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        logger.info("Creating a new customer");

        try {
            final Customer savedCustomer = customerRepository.save(customer);

            logger.info("Created customer with id {}", savedCustomer.getId());
            return savedCustomer;
        } catch (Exception e) {
            throw new CustomerCreationException("Failed to create customer: " + e.getMessage());
        }
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        logger.info("Updating customer with id {}", id);

        final Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }

        final Customer customerToUpdate = customerOptional.get();
        customerToUpdate.setBirthdate(customer.getBirthdate());
        customerToUpdate.setFirstName(customer.getFirstName());
        customerToUpdate.setLastName(customer.getLastName());
        customerToUpdate.setEmail(customer.getEmail());

        try {
            final Customer updatedCustomer = customerRepository.save(customerToUpdate);
            logger.info("Updated customer with id {}", updatedCustomer.getId());
            return updatedCustomer;
        } catch (Exception e) {
            throw new CustomerUpdateException("Failed to update customer due to data integrity violation");
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return List.of();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {
        logger.info("Deleting customer with id {}", id);

        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }

        customerRepository.deleteById(id);
        logger.info("Deleted customer with id {}", id);
    }
}
