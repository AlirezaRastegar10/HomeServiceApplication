package com.example.dto.subservice;


import com.example.entity.Services;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubServiceDto {

    @NotBlank(message = "sub service name cannot be empty.")
    @Size(min = 3, message = "sub service name should have at least 3 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "sub service name can not have number and sign(!,@,#,%,...).")
    String name;

    @Positive(message = "base price cannot be negative or zero.")
    @NotNull(message = "sub service base price cannot be empty.")
    @Digits(integer = 20, fraction = 0, message = "the base price must be a number")
    Long basePrice;

    @NotBlank(message = "sub service explanation cannot be empty.")
    @Size(min = 3, message = "sub service explanation should have at least 3 characters.")
    String explanation;

    @NotNull(message = "the service must not be empty.")
    Services services;
}
