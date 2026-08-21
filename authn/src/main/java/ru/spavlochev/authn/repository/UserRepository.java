package ru.spavlochev.authn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.spavlochev.authn.entity.AppUser;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
