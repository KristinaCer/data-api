package com.kristina.dataapi;

import com.kristina.dataapi.customer.CustomerService;
import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.dialog.DialogService;
import com.kristina.dataapi.dialog.model.Dialog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApiApplication.class, args);
    }
    @Bean
    CommandLineRunner initDB(DialogService dialogService, CustomerService customerService){
        return  args -> {
            dialogService.saveDialog(new Dialog(new Customer()));
            dialogService.saveDialog(new Dialog(new Customer()));
            dialogService.saveDialog(new Dialog(new Customer()));
            dialogService.saveDialog(new Dialog(new Customer()));

        };
    }
}
