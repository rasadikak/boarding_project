package com.kaushani.demo.auth;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public boolean VerifyPassword(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword);
    }

    public String login(String email, String password) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(email);

            if (existingUser.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            User user = existingUser.get();

            if (!VerifyPassword(password, user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            return jwtUtil.generateToken(user.getEmail(), user.getRole());
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}