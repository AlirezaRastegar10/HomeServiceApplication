package com.example.dto.offer;


import com.example.entity.Orders;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOfferDto {

    @Positive(message = "proposed price cannot be negative or zero.")
    @NotNull(message = "proposed price cannot be empty.")
    @Digits(integer = 20, fraction = 0, message = "the proposed price must be a number")
    Long proposedPrice;

    @NotBlank(message = "suggested time cannot be empty.")
    String suggestedDate;

    @NotBlank(message = "duration of work cannot be empty.")
    String workTime;

    @NotNull(message = "the order must not be empty.")
    Orders order;
}
