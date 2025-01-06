package ru.practicum.ewm.error.exceptions;

public class ValidationException extends BadRequestException {
    public ValidationException(String message) {
        super(message);
    }
}
