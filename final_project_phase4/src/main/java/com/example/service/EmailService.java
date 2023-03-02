package com.example.service;

import com.example.entity.Expert;

public interface EmailService {

    void sendMail(Expert expert, String url);
}
