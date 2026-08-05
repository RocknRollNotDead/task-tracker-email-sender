package ru.codeportfolio.emailsender.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.codeportfolio.emailsender.dto.EmailDto;

@Service
public class EmailService {

    private final JavaMailSender mailSender;


    // слушает backend и слушает планировщик

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(EmailDto emailDto){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailDto.email());
        message.setSubject(emailDto.header());
        message.setText(emailDto.text());

        mailSender.send(message);
    }




}
