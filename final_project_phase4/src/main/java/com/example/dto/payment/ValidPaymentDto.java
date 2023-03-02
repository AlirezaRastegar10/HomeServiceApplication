package com.example.dto.payment;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ValidPaymentDto {

    @Pattern(regexp = "^\\d{4}$", message = "the first four digits of the card must be numbers.")
    String card1;

    @Pattern(regexp = "^\\d{4}$", message = "the second four digits of the card must be numbers.")
    String card2;

    @Pattern(regexp = "^\\d{4}$", message = "the third four digits of the card must be numbers.")
    String card3;

    @Pattern(regexp = "^\\d{4}$", message = "the forth four digits of the card must be numbers.")
    String card4;

    @Pattern(regexp = "^\\d{6,12}$", message = "the second password of the card must be numbers.")
    String secondPassword;

    @Pattern(regexp = "^\\d{3,4}$", message = "the cvv2 of the card must be numbers.")
    String cvv2;

    @Pattern(regexp = "^\\d{2}$", message = "the year of the card must be numbers.")
    String year;

    @Pattern(regexp = "^\\d{2}$", message = "the month of the card must be numbers.")
    String month;

    @Email(message = "email pattern not valid.")
    String email;
}
