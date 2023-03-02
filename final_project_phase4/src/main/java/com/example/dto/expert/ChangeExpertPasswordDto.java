package com.example.dto.expert;

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
public class ChangeExpertPasswordDto {

    @NotBlank(message = "password cannot be empty.")
    @Size(min = 8, max = 8, message = "password must be 8 characters.")
    @Pattern(regexp = "^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$", message = "the password must contain numbers and letters.")
    String password;

    @NotBlank(message = "password cannot be empty.")
    @Size(min = 8, max = 8, message = "password must be 8 characters.")
    @Pattern(regexp = "^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+$", message = "the password must contain numbers and letters.")
    String repeatPassword;
}
