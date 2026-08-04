package ru.codeportfolio.emailsender;



import org.springframework.stereotype.Service;

@Service
public class EmailService {


    // слушает backend и слушает планировщик

    public final static String TEXT_MUST_BE_FORMATTED = """
            
            Приветствуем, %s!
            Вы зарегистрировались в нашем сервисе Task Ledger на сайте %s!
            
            Приятного пользования!
            
            """;
    public final static String DOMAIN = "codeportfolio.ru";
    private final EmailKafkaTemplate emailKafkaTemplate;


    public EmailService(EmailKafkaTemplate emailKafkaTemplate) {
        this.emailKafkaTemplate = emailKafkaTemplate;
    }

    public void sendWelcome(EmailDto emailDto){

        // kafka
        // email отправка
    }




}
