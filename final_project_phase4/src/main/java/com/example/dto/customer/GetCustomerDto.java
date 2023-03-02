package com.example.dto.customer;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetCustomerDto {

    Long id;
    String firstname;
    String lastname;
    LocalDateTime registerDate;
    Long credit;
    String role;
}
