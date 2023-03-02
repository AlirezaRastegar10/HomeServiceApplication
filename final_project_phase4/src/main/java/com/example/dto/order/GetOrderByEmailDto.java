package com.example.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOrderByEmailDto {

    @NotBlank(message = "email cannot be empty.")
    @Email(message = "the email pattern is incorrect.")
    String email;
}
