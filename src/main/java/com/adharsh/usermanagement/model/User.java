package com.adharsh.usermanagement.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public static User create(String email,String name, String passwordHash){
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (passwordHash == null) throw new IllegalArgumentException("password required");

        User user = new User();
        user.email = email.trim().toLowerCase();
        user.name = name.trim();
        user.passwordHash = passwordHash;
        user.active = true;
        return user;
    }

    @PrePersist
    public void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void deactivate(){
        this.active = false;

    }

    public void activate(){
        this.active = true;
    }

    public void updatePassword(String newHash){
        if (newHash == null || newHash.isBlank()) {
            throw new IllegalArgumentException("password required");
        }
        this.passwordHash = newHash;
    }
}
