package com.example.dto.subservice;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetSubServiceDto {

    Long id;
    String name;

    @Positive(message = "base price cannot be negative or zero.")
    @NotNull(message = "sub service base price cannot be empty.")
    @Digits(integer = 20, fraction = 0, message = "the base price must be a number")
    Long basePrice;

    @NotBlank(message = "sub service explanation cannot be empty.")
    @Size(min = 3, message = "sub service explanation should have at least 3 characters.")
    String explanation;
}
