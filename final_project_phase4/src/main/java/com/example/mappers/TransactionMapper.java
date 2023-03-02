package com.example.mappers;


import com.example.dto.transaction.TransactionDto;
import com.example.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    List<TransactionDto> transactionListToDtoList(List<Transaction> transactionList);
}
