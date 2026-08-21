package ru.spavlochev.usersvc.dto;

public record ErrorResponseDto(
        Integer code,
        String message) {
}
