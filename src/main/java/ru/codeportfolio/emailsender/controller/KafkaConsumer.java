package ru.codeportfolio.emailsender.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.codeportfolio.emailsender.dto.EmailDto;
import ru.codeportfolio.emailsender.service.EmailService;
import tools.jackson.databind.ObjectMapper;

@Controller
public class KafkaConsumer {
    private final EmailService emailService;

    public KafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }


    @KafkaListener(topics = "EMAIL_SENDING_TASKS")
    public void consume(String json){
        ObjectMapper mapper = new ObjectMapper();
        EmailDto emailDto = mapper.readValue(json, EmailDto.class);
        emailService.send(emailDto);
    }
}
