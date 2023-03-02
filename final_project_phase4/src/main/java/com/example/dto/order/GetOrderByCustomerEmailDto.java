package com.example.dto.order;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOrderByCustomerEmailDto {

    @NotNull(message = "the order id must not be empty.")
    Long id;
}
