package com.example.service;

import com.example.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TransactionService {

    void create(Transaction transaction);
    List<Transaction> search(Specification<Transaction> spec);
}
