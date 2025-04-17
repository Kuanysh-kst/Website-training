package com.example.demo.controllers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("/send-mail")
    public String sendEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("tatarasan26@gmail.com");
            message.setTo("kuanysh4646@gmail.com");
            message.setSubject("Simple test");
            message.setText("Simple env text");

            mailSender.send(message);
            return "success!";
        } catch (Exception cause) {
            return cause.getMessage();
        }
    }
}
