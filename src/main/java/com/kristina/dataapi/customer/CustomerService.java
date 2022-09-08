package com.kristina.dataapi.customer;

import com.kristina.dataapi.customer.model.Customer;

public interface CustomerService {
    Customer save(Customer customerDTO);

    Customer get(Long customerId);
}
