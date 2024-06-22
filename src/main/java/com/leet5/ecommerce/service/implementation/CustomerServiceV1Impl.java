package com.leet5.ecommerce.service.implementation;

import com.leet5.ecommerce.exception.customer.CustomerCreationException;
import com.leet5.ecommerce.exception.customer.CustomerNotFoundException;
import com.leet5.ecommerce.exception.customer.CustomerUpdateException;
import com.leet5.ecommerce.model.dto.CustomerDTO;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.repository.CustomerRepository;
import com.leet5.ecommerce.service.CustomerService;
import com.leet5.ecommerce.util.CustomerMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerServiceV1Impl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceV1Impl.class);
    private static final int VERSION = 1;

    private final CustomerRepository customerRepository;

    public CustomerServiceV1Impl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        logger.info("Creating a new customer");

        try {
            final var customer = CustomerMapper.toCustomer(customerDTO);
            final var savedCustomer = customerRepository.save(customer);

            logger.info("Created customer with id {}", savedCustomer.getId());
            return CustomerMapper.toCustomerDTO(savedCustomer);
        } catch (Exception e) {
            throw new CustomerCreationException("Failed to create customer: " + e.getMessage());
        }
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        logger.info("Updating customer with id {}", id);

        final Customer customerToUpdate = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        customerToUpdate.setBirthdate(customer.birthdate());
        customerToUpdate.setFirstName(customer.firstName());
        customerToUpdate.setLastName(customer.lastName());
        customerToUpdate.setEmail(customer.email());

        try {
            final Customer updatedCustomer = customerRepository.save(customerToUpdate);
            logger.info("Updated customer with id {}", updatedCustomer.getId());
            return CustomerMapper.toCustomerDTO(updatedCustomer);
        } catch (Exception e) {
            throw new CustomerUpdateException("Failed to update customer due to data integrity violation");
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers(int page, int size) {
        logger.info("Getting all customers");

        final PageRequest pageRequest = PageRequest.of(page, size);

        final List<Customer> customers = customerRepository.findAll(pageRequest).getContent();
        logger.info("Found {} customers", customers.size());

        return customers.stream().map(CustomerMapper::toCustomerDTO).toList();
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        logger.info("Getting customer with id {}", id);

        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        logger.info("Found customer with id {}", id);
        return CustomerMapper.toCustomerDTO(customer);
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

    @Override
    public int getVersion() {
        return VERSION;
    }
}
