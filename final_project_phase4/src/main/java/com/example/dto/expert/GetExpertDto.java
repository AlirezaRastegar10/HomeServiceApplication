package com.example.dto.expert;


import com.example.entity.enumeration.ExpertStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetExpertDto {

    Long id;
    String firstname;
    String lastname;
    LocalDateTime registerDate;
    ExpertStatus status;
    Long credit;
    Integer score;
    String role;
}
