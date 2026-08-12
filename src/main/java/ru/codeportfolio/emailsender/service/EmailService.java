package ru.codeportfolio.emailsender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.codeportfolio.emailsender.dto.EmailDto;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final String FINAL_STRING =
            """
                    
                    
                    Не отвечайте на это письмо.
                    
                    """;

    // слушает backend и слушает планировщик

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(EmailDto emailDto) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("tasker@codeportfolio.ru");
        message.setTo(emailDto.email());
        message.setSubject(emailDto.header());
        message.setText(emailDto.text() + FINAL_STRING);

        try {
            mailSender.send(message);
            log.info("Send mail {} to {}", emailDto.header(), emailDto.email());
        } catch (MailException e) {
            log.error("Can't send mail to {}", emailDto.email());
            throw e;
        }
    }


}
