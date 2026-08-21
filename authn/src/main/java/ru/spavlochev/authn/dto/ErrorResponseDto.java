package ru.spavlochev.authn.dto;

public record ErrorResponseDto(
        Integer code,
        String message) {
}
