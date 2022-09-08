package com.kristina.dataapi.customer;

import com.kristina.dataapi.customer.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody Customer customerDTO) {
        Customer customer = customerService.save(customerDTO);
        return ResponseEntity.ok(customer);
    }
}
