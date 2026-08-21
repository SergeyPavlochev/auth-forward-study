package ru.spavlochev.usersvc.exception;

public class AccessRightsViolationException extends RuntimeException {
    public AccessRightsViolationException(String message) {
        super(message);
    }
}
