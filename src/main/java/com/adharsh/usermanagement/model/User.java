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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public static User create(String email,String name, String passwordHash, Instant now){
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email required");
        if (passwordHash == null) throw new IllegalArgumentException("password required");

        User user = new User();
        user.email = email;
        user.name = name;
        user.passwordHash = passwordHash;
        user.active = true;
        user.createdAt = now;
        user.updatedAt = now;
        return user;
    }

    public void deactivate(Instant now){
        this.active = false;
        this.updatedAt = now;

    }

    public void activate(Instant now){
        this.active = true;
        this.updatedAt = now;
    }

    public void updatePassword(String newHash, Instant now){
        this.passwordHash = newHash;
        this.updatedAt = now;
    }
}
