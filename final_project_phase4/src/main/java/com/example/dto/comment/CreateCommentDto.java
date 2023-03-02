package com.example.dto.comment;


import com.example.entity.Orders;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCommentDto {

    @NotNull(message = "the orders must not be empty.")
    Orders orders;

    @Positive(message = "score cannot be negative or zero.")
    @NotNull(message = "score cannot be empty.")
    @Min(value = 1, message = "the score must be between 1 and 5.")
    @Max(value = 5, message = "the score must be between 1 and 5.")
    @Digits(integer = 1, fraction = 0, message = "the score must be a number.")
    Integer score;

    String content;
}
