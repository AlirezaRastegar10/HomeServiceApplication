package com.example.dto.expert;


import com.example.entity.SubService;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpertToSubServiceDto {

    Long id;

    @NotNull(message = "the sub service must not be empty.")
    SubService subService;
}
