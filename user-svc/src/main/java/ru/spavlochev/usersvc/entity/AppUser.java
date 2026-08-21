package ru.spavlochev.usersvc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.ProxyUtils;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "app_user", schema = "usersvc")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "user_name", length = 250)
    private String userName;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(getClass() == obj.getClass() || ProxyUtils.getUserClass(this) == ProxyUtils.getUserClass(obj))) {
            return false;
        }
        AppUser other = (AppUser) obj;
        return Objects.equals(getEmail(), other.getEmail());
    }
}
