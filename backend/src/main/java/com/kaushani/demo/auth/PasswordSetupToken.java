package com.kaushani.demo.auth;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "password_setup_tokens")
public class PasswordSetupToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Boolean used = false;

    @Column(nullable = false)
    private Timestamp createdAt;

    public PasswordSetupToken() {}

    public PasswordSetupToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.used = false;
    }

    public Long getId() { return id; }
    public String getToken() { return token; }
    public User getUser() { return user; }
    public Boolean getUsed() { return used; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setToken(String token) { this.token = token; }
    public void setUser(User user) { this.user = user; }
    public void setUsed(Boolean used) { this.used = used; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}