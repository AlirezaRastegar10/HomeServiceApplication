package com.example.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Transaction extends BaseEntity<Long> {

    @OneToOne
    Customer customer;

    @ManyToOne
    Expert expert;

    @CreationTimestamp
    LocalDateTime registerDate;

    @OneToOne
    SubService subService;

    @OneToOne
    Services services;

    Long price;

    String paymentType;

    String status;

    public Transaction(Customer customer, Expert expert, SubService subService, Services services, Long price, String paymentType, String status) {
        this.customer = customer;
        this.expert = expert;
        this.subService = subService;
        this.services = services;
        this.price = price;
        this.paymentType = paymentType;
        this.status = status;
    }
}
