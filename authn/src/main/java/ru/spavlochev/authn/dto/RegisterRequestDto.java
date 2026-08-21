package ru.spavlochev.authn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 100)
        String password) {
}
