package ru.spavlochev.usersvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        String userName,
        String firstName,
        String lastName,
        @NotBlank @Email String email,
        String phone) {
}
