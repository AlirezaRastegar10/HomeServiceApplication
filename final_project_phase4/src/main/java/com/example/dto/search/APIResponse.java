package com.example.dto.search;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class APIResponse {

    Object data;
    String message;
    HttpStatus responseCode;
}
