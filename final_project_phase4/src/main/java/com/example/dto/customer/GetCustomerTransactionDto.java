package com.example.dto.customer;


import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetCustomerTransactionDto {

    Long id;
    String firstname;
    String lastname;
    String role;
}
