package ru.spavlochev.authn.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spavlochev.authn.dto.AuthResponseDto;
import ru.spavlochev.authn.dto.LoginRequestDto;
import ru.spavlochev.authn.dto.RegisterRequestDto;
import ru.spavlochev.authn.service.AuthService;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDto request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validate(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Также проверяем cookie (если токен хранится там)
            String cookie = request.getHeader("Cookie");
            if (cookie == null || !cookie.contains("token=")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // Извлекаем токен из cookie
            authHeader = "Bearer " + extractTokenFromCookie(cookie);
        }

        try {
            String token = authHeader.substring(7);
            UUID userId = authService.validateToken(token);

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-User-Id", userId.toString());

            return ResponseEntity.ok().headers(headers).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String extractTokenFromCookie(String cookie) {
        for (String part : cookie.split(";")) {
            if (part.trim().startsWith("token=")) {
                return part.trim().substring(6);
            }
        }
        return null;
    }
}
