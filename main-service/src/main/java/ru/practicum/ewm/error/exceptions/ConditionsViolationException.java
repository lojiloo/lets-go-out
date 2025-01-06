package ru.practicum.ewm.error.exceptions;

public class ConditionsViolationException extends RuntimeException {
    public ConditionsViolationException(String message) {
        super(message);
    }
}
