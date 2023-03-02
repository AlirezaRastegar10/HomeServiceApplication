package com.example.service.impl;


import com.example.entity.Transaction;
import com.example.repository.TransactionRepository;
import com.example.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void create(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> search(Specification<Transaction> spec) {
        return transactionRepository.findAll(spec);
    }
}
