package com.example.dto.offer;


import com.example.dto.order.GetOrderForExpertDto;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOfferDto {

    Long id;
    LocalDateTime registerDateAndTime;
    Long proposedPrice;
    LocalDateTime suggestedTime;
    LocalDateTime durationOfWork;
    GetOrderForExpertDto order;
}
