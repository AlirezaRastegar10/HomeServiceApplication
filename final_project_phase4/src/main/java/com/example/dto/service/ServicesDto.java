package com.example.dto.service;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServicesDto {

    @NotBlank(message = "service name cannot be empty.")
    @Size(min = 3, message = "service name should have at least 3 characters.")
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-])*$", message = "service name can not have number and sign(!,@,#,%,...).")
    String name;
}
