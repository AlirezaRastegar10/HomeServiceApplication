package com.example.service.impl;


import com.example.entity.Expert;
import com.example.exceptions.email.EmailException;
import com.example.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(Expert expert, String url) {
        String request = url + "?token=" + expert.getToken();
        String subject = "please verify your account";

        String content = "<p>please click on the following link to activate your account:</p>";
        content += "<h2><a href=\"" + request + "\">verify</a></h2>";
        content += "<p>Specialized home services with a Achareh :)</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setSubject(subject);
            helper.setFrom("Achare@Support.com");
            helper.setTo(expert.getEmail());
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailException("there is a problem with the email message.");
        } catch (MailSendException e) {
            throw new EmailException("there is a problem sending the email.");
        }
    }
}
