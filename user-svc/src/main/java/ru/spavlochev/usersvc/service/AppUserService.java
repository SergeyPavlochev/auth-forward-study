package ru.spavlochev.usersvc.service;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.spavlochev.usersvc.dto.CreateUserDto;
import ru.spavlochev.usersvc.dto.UpdateUserDto;
import ru.spavlochev.usersvc.dto.UserDto;
import ru.spavlochev.usersvc.mapper.AppUserMapper;
import ru.spavlochev.usersvc.repository.AppUserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;

    @Transactional
    public UserDto createBy(@Nonnull UUID userId, @Nonnull CreateUserDto createUserDto) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(createUserDto, "createUserDto must not be null");

        var newAppUser = mapper.toEntity(userId, createUserDto);
        var createdAppUser = repository.save(newAppUser);
        return mapper.toDto(createdAppUser);
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> getBy(@Nonnull UUID userId) {
        Assert.notNull(userId, "userId must not be null");

        if (!repository.existsByUserId(userId)) {
            throw new EntityNotFoundException("User profile not found");
        }
        return repository.findByUserId(userId)
                .map(mapper::toDto);
    }

    @Transactional
    public UserDto updateBy(@Nonnull UUID userId, @Nonnull UpdateUserDto updateUserDto) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(updateUserDto, "updateUserDto must not be null");

        return repository.findByUserId(userId)
                .map(user -> {
                    var userToBeUpdated = mapper.toEntity(user.getId(), userId, user.getEmail(), updateUserDto);
                    var updatedUser = repository.save(userToBeUpdated);
                    return mapper.toDto(updatedUser);
                })
                .orElseThrow(() -> new EntityNotFoundException("User profile not found"));
    }

    @Transactional
    public void deleteBy(@Nonnull UUID userId) {
        Assert.notNull(userId, "userId must not be null");

        repository.findByUserId(userId)
                .ifPresent(repository::delete);
    }
}
