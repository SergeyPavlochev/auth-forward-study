package ru.spavlochev.authn.dto;

public record AuthResponseDto(
        String token,
        String userId) {
}
