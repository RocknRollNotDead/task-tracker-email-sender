package ru.codeportfolio.emailsender;



import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class EmailController {
    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }


}
