package ru.spavlochev.authn.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import ru.spavlochev.authn.dto.AuthResponseDto;
import ru.spavlochev.authn.dto.CreateUserProfileRqDto;
import ru.spavlochev.authn.dto.LoginRequestDto;
import ru.spavlochev.authn.dto.RegisterRequestDto;
import ru.spavlochev.authn.entity.AppUser;
import ru.spavlochev.authn.repository.UserRepository;
import ru.spavlochev.authn.security.JwtUtil;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RestClient userSvcRestClient;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        userSvcRestClient = RestClient.builder()
                .baseUrl("http://user-svc-svc:80/api/user-profile")
                .build();
    }

    @Transactional
    public void register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EntityExistsException("Email already exists");
        }
        var user = userRepository.save(AppUser.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build());

        userSvcRestClient.post()
                .header("X-User-Id", user.getId().toString())
                .body(new CreateUserProfileRqDto(request.email()))
                .retrieve()
                .toBodilessEntity();
    }

    @Transactional
    public AuthResponseDto login(LoginRequestDto request) {
        AppUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return new AuthResponseDto(token, user.getId().toString());
    }

    public UUID validateToken(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        return jwtUtil.getUserIdFromToken(token);
    }
}
