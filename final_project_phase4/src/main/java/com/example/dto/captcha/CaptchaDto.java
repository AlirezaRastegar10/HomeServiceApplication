package com.example.dto.captcha;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaDto {

    @NotNull
    String name;

    @NotNull
    String captchaResponse;
}
