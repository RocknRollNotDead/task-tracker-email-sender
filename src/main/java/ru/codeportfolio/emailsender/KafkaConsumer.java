package ru.codeportfolio.emailsender;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {


    // dto

    @KafkaListener(topics = "EMAIL_SENDING_TASKS")
    public void consume(String json){
        ObjectMapper mapper = new ObjectMapper();
        EmailDto emailDto = mapper.readValue(json, EmailDto.class);

    }
}
