package ru.spavlochev.usersvc.dto;

public record UserDto(
        String userName,
        String firstName,
        String lastName,
        String email,
        String phone) {
}
