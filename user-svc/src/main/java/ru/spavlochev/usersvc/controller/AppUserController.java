package ru.spavlochev.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spavlochev.usersvc.dto.CreateUserDto;
import ru.spavlochev.usersvc.dto.UpdateUserDto;
import ru.spavlochev.usersvc.dto.UserDto;
import ru.spavlochev.usersvc.service.AppUserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-profile")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService service;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestHeader(name = "X-User-Id") UUID userId,
                                              @RequestBody @Validated CreateUserDto createUserDto) {
        var userDto = service.createBy(userId, createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDto);
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserById(@RequestHeader(name = "X-User-Id") UUID userId) {
        return service.getBy(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUserById(@RequestHeader(name = "X-User-Id") UUID userId,
                                                  @RequestBody @Validated UpdateUserDto updateUserDto) {
        var userDto = service.updateBy(userId, updateUserDto);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserById(@RequestHeader(name = "X-User-Id") UUID userId) {
        service.deleteBy(userId);
        return ResponseEntity.noContent().build();
    }
}
