package com.example.dto.customer;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetModifiedCustomerPasswordTimeDto {

    LocalDateTime modifiedTime;
    String modifiedText;
}
