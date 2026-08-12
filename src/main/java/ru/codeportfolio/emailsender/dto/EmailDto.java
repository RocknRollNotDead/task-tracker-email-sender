package ru.codeportfolio.emailsender.dto;

public record EmailDto(
        String email,
        String header,
        String text
) {
}
