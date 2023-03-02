package com.example.dto.subservice;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSubServiceDto {

    Long id;

    @Positive(message = "base price cannot be negative or zero.")
    @Digits(integer = 20, fraction = 0, message = "the base price must be a number")
    Long basePrice;

    @Size(min = 3, message = "sub service explanation should have at least 3 characters.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "sub service explanation cannot be empty.")
    String explanation;
}
