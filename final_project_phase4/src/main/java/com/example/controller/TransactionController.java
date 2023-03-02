package com.example.controller;


import com.example.dto.search.APIResponse;
import com.example.entity.Transaction;
import com.example.mappers.TransactionMapperImpl;
import com.example.search.SearchCriteria;
import com.example.search.SearchDto;
import com.example.search.TransactionSpecificationBuilder;
import com.example.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    private final TransactionServiceImpl transactionService;
    private final TransactionMapperImpl transactionMapper;

    @PostMapping("/search")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<APIResponse> findAll(@RequestBody SearchDto searchDto) {
        APIResponse apiResponse = new APIResponse();
        TransactionSpecificationBuilder builder = new TransactionSpecificationBuilder();
        List<SearchCriteria> criteriaList = searchDto.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(searchDto
                        .getDataOption());
                builder.with(x);
            });
        }

        List<Transaction> transactionList = transactionService.search(builder.build());
        apiResponse.setData(transactionMapper.transactionListToDtoList(transactionList));
        apiResponse.setResponseCode(HttpStatus.OK);
        apiResponse.setMessage("Successfully retrieved customer record");

        return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
    }
}
