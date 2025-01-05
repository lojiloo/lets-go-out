package ru.practicum.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BadRequestException extends RuntimeException {
    private final LocalDateTime thrown;

    public BadRequestException(String message, LocalDateTime thrown) {
        super(message);

        this.thrown = thrown;
    }
}
