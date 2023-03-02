package com.example.service;


import com.example.dto.customer.GetModifiedCustomerPasswordTimeDto;
import com.example.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CustomerService {

    Customer findById(Long id);
    List<Customer> findAll();
    GetModifiedCustomerPasswordTimeDto changePassword(Customer customer);
    void updateCustomerCredit(Long money, Customer customer);
    List<Customer> search(Specification<Customer> spec);
    Customer showCredit();
}
