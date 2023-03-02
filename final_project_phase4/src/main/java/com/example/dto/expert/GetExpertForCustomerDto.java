package com.example.dto.expert;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetExpertForCustomerDto {

    Long id;
    String firstname;
    String lastname;
    Integer score;
}
