package com.example.dto.order;


import com.example.dto.customer.GetCustomerDto;
import com.example.dto.subservice.GetSubServiceDto;
import com.example.entity.enumeration.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOrderDto {

    Long id;
    Long proposedPrice;
    String description;
    LocalDateTime dateAndTime;
    String address;
    OrderStatus orderStatus;
    GetCustomerDto customer;
    GetSubServiceDto subService;
}
