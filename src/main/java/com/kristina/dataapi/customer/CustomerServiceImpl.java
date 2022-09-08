package com.kristina.dataapi.customer;

import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customerDTO) {
        return customerRepository.save(customerDTO);
    }
}
