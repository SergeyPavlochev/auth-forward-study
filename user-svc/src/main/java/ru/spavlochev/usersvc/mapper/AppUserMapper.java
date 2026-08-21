package ru.spavlochev.usersvc.mapper;

import org.springframework.stereotype.Component;
import ru.spavlochev.usersvc.dto.CreateUserDto;
import ru.spavlochev.usersvc.dto.UpdateUserDto;
import ru.spavlochev.usersvc.dto.UserDto;
import ru.spavlochev.usersvc.entity.AppUser;

import java.util.UUID;

@Component
public class AppUserMapper {

    public UserDto toDto(AppUser user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone());
    }

    public AppUser toEntity(UUID userId, CreateUserDto createUserDto) {
        if (createUserDto == null) {
            return null;
        }

        return AppUser.builder()
                .userId(userId)
                .userName(createUserDto.userName())
                .firstName(createUserDto.firstName())
                .lastName(createUserDto.lastName())
                .email(createUserDto.email())
                .phone(createUserDto.phone())
                .build();
    }

    public AppUser toEntity(Long id, UUID userId, String email, UpdateUserDto updateUserDto) {
        if (updateUserDto == null) {
            return null;
        }

        return AppUser.builder()
                .id(id)
                .userId(userId)
                .userName(updateUserDto.userName())
                .firstName(updateUserDto.firstName())
                .lastName(updateUserDto.lastName())
                .email(email)
                .phone(updateUserDto.phone())
                .build();
    }
}
