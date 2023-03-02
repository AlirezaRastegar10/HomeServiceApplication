package com.example.dto.offer;

import com.example.dto.expert.GetExpertForCustomerDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOfferForCustomerDto {

    Long id;
    LocalDateTime registerDateAndTime;
    Long proposedPrice;
    LocalDateTime suggestedTime;
    LocalDateTime durationOfWork;
    GetExpertForCustomerDto expert;
}
