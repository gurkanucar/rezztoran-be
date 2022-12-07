package com.rezztoran.rezztoranbe.service;

import com.rezztoran.rezztoranbe.dto.request.MailModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {


    private final JavaMailSender emailSender;

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(MailModel mailModel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailModel.getTo());
        message.setSubject(mailModel.getSubject());
        message.setText(mailModel.getText());
        emailSender.send(message);
    }

}
