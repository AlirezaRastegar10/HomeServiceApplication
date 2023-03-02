package com.example.controller;


import com.example.dto.customer.ChangeCustomerPasswordDto;
import com.example.dto.customer.GetCustomerCreditDto;
import com.example.dto.customer.GetCustomerDto;
import com.example.dto.customer.GetModifiedCustomerPasswordTimeDto;
import com.example.dto.search.APIResponse;
import com.example.entity.Customer;
import com.example.mappers.CustomerMapperImpl;
import com.example.search.CustomerSpecificationBuilder;
import com.example.search.SearchCriteria;
import com.example.search.SearchDto;
import com.example.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final CustomerMapperImpl customerMapper;

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('Admin')")
    public GetCustomerDto findById(@PathVariable Long id) {
        return customerMapper.customerToDto(customerService.findById(id));
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('Admin')")
    public List<GetCustomerDto> findAll() {
        return customerMapper.customerListToDtoList(customerService.findAll());
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasRole('Customer')")
    public GetModifiedCustomerPasswordTimeDto changePassword(@Valid @RequestBody ChangeCustomerPasswordDto changeCustomerPasswordDto) {
        Customer customer = customerMapper.changePasswordDtoToCustomer(changeCustomerPasswordDto);
        return customerService.changePassword(customer);
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<APIResponse> findAll(@RequestBody SearchDto searchDto) {
        APIResponse apiResponse = new APIResponse();
        CustomerSpecificationBuilder builder = new CustomerSpecificationBuilder();
        List<SearchCriteria> criteriaList = searchDto.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(searchDto
                        .getDataOption());
                builder.with(x);
            });
        }

        List<Customer> customerList = customerService.search(builder.build());
        apiResponse.setData(customerMapper.customerListToDtoList(customerList));
        apiResponse.setResponseCode(HttpStatus.OK);
        apiResponse.setMessage("Successfully retrieved customer record");

        return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
    }

    @GetMapping("/showCredit")
    @PreAuthorize("hasRole('Customer')")
    public GetCustomerCreditDto showCredit() {
        return customerMapper.customerToCreditDto(customerService.showCredit());
    }
}
