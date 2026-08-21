package ru.spavlochev.usersvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spavlochev.usersvc.entity.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);
}
