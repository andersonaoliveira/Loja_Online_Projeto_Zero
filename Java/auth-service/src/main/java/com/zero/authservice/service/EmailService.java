package com.zero.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendPasswordResetToken(String email, String token) {
        String subject = "Password Reset Request";
        String text = "To reset your password, click the link below:\n" + "http://yourdomain.com/reset-password?token="
                + token;
        sendEmail(email, subject, text);
    }
}