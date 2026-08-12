package ru.codeportfolio.emailsender.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.codeportfolio.emailsender.dto.EmailDto;
import ru.codeportfolio.emailsender.service.EmailService;
import tools.jackson.databind.ObjectMapper;

@Controller
public class KafkaConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "EMAIL_SENDING_TASKS")
    public void consume(String json) {
        EmailDto emailDto = objectMapper.readValue(json, EmailDto.class);
        emailService.send(emailDto);
    }
}
