package com.kristina.dataapi.dialog;

import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.customer.repository.CustomerRepository;
import com.kristina.dataapi.data.model.Dialog;
import com.kristina.dataapi.data.repository.DialogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dialogs")
public class DialogController {

    private final DialogRepository dialogRepository;
    private final CustomerRepository customerRepository;

    public DialogController(DialogRepository dialogRepository, CustomerRepository customerRepository) {
        this.dialogRepository = dialogRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Dialog> save(@PathVariable Long customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        Dialog dialog = dialogRepository.save(new Dialog(customer));
        return ResponseEntity.ok(dialog);
    }
}
