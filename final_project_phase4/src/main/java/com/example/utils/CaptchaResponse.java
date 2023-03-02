package com.example.utils;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaResponse {

    boolean success;
    LocalDateTime challenge_ts;
    String hostname;
    @JsonProperty("error-codes")
    List<String> errorCodes;
}
