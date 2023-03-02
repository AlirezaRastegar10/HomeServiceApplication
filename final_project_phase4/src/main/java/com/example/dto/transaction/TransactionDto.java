package com.example.dto.transaction;


import com.example.dto.customer.GetCustomerTransactionDto;
import com.example.dto.expert.GetExpertTransactionDto;
import com.example.dto.service.GetServicesDto;
import com.example.dto.subservice.GetSubServiceTransactionDto;
import com.example.entity.enumeration.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDto {

    Long id;
    GetCustomerTransactionDto customer;
    GetExpertTransactionDto expert;
    LocalDateTime registerDate;
    GetSubServiceTransactionDto subService;
    GetServicesDto services;
    Long price;
    String paymentType;
    OrderStatus status;
}
