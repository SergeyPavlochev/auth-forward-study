package ru.spavlochev.usersvc.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.spavlochev.usersvc.dto.ErrorResponseDto;
import ru.spavlochev.usersvc.exception.AccessRightsViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("Отсутствует запись в БД: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(AccessRightsViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessRightsViolationException(AccessRightsViolationException e) {
        log.error("Нарушение прав доступа: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), "Access denied"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException e) {
        log.error("Непредвиденная ошибка: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(new ErrorResponseDto(500, e.getClass().getSimpleName() + ": " + e.getMessage()));
    }
}
