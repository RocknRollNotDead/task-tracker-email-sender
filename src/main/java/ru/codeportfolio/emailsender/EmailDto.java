package ru.codeportfolio.emailsender;

public record EmailDto (
        String email,
        String username,
        String text
) {
}
