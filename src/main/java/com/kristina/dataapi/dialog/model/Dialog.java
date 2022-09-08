package com.kristina.dataapi.dialog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.consent.model.Consent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    @JoinColumn(name = "consent_id")
    @JsonIgnore
    Consent consent;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    Customer customer;

    public Dialog(Customer customer) {
        this.customer = customer;
    }
}
