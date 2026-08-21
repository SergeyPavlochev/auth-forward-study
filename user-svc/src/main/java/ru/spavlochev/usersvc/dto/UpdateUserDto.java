package ru.spavlochev.usersvc.dto;

public record UpdateUserDto(
        String userName,
        String firstName,
        String lastName,
        String phone) {
}
