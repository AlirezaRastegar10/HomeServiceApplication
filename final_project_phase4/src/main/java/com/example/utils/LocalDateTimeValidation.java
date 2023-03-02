package com.example.utils;

import com.example.exceptions.time.LessTimeException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeValidation {

    public LocalDateTime validator(String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        if (localDateTime.isBefore(LocalDateTime.now())) {
            throw new LessTimeException("the entered date is less than today's date.");
        }
        return localDateTime;
    }
}
