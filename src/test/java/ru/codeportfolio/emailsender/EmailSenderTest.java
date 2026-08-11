package ru.codeportfolio.emailsender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.codeportfolio.emailsender.controller.KafkaConsumer;
import ru.codeportfolio.emailsender.dto.EmailDto;
import ru.codeportfolio.emailsender.service.EmailService;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    public static final String EMAIL = "1450989432@a.ru";
    public static final String HEADER = "Письмо тест";
    public static final String TEXT = "1234";

    public static final String EMAIL1 = "12131@a.ru";
    public static final String HEAD_1 = "head1";
    public static final String TEXT1 = "kposdpogf";

    public static final String EMAIL2 = "214235@a.ru";
    public static final String HEAD_2 = "head2";
    public static final String TEXT2 = "ert346t";

    @Mock
    private JavaMailSender mailSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EmailDto emailDto = new EmailDto(EMAIL, HEADER, TEXT);

    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    void setUp() {
        EmailService emailService = new EmailService(mailSender);
        kafkaConsumer = new KafkaConsumer(emailService);
    }


    @Test
    void shouldSendMessageFromKafka() {

        String kafkaMessage = objectMapper.writeValueAsString(emailDto);


        kafkaConsumer.consume(kafkaMessage);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sent = captor.getValue();
        assertThat(sent.getTo()).containsExactly(EMAIL);
        assertThat(sent.getSubject()).isEqualTo(HEADER);
        assertThat(sent.getText()).contains(TEXT);
    }

    @Test
    void dontShouldSendEmail() {

        String brokenMessage = "{not-valid-json";

        assertThatThrownBy(() -> kafkaConsumer.consume(brokenMessage))
                .isInstanceOf(Exception.class);

        org.mockito.Mockito.verifyNoInteractions(mailSender);
    }

    @Test
    void shouldSend2messagesNoRaceConditions() {

        kafkaConsumer.consume(objectMapper.writeValueAsString(new EmailDto(
                EMAIL1, HEAD_1, TEXT1)));
        kafkaConsumer.consume(objectMapper.writeValueAsString(new EmailDto(
                EMAIL2, HEAD_2, TEXT2)));

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(2)).send(captor.capture());

        assertThat(captor.getAllValues())
                .extracting(SimpleMailMessage::getTo)
                .containsExactly(new String[]{EMAIL1}, new String[]{EMAIL2});
    }

}
