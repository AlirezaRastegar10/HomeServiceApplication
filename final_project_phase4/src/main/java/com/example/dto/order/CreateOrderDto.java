package com.example.dto.order;


import com.example.entity.SubService;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderDto {

    @Positive(message = "proposed price cannot be negative or zero.")
    @NotNull(message = "proposed price cannot be empty.")
    @Digits(integer = 20, fraction = 0, message = "the proposed price must be a number")
    Long proposedPrice;

    @NotBlank(message = "description cannot be empty.")
    @Size(min = 3, message = "description should have at least 3 characters.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "description cannot be empty.")
    String description;

    @NotBlank(message = "time cannot be empty.")
    String time;

    @NotBlank(message = "address cannot be empty.")
    @Size(min = 3, message = "address should have at least 3 characters.")
    @Pattern(regexp = "^(?!\\s*$).+", message = "address cannot be empty.")
    String address;

    @NotNull(message = "the sub service must not be empty.")
    SubService subService;
}
