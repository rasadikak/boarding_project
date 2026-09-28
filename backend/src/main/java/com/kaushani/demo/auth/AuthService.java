package com.kaushani.demo.auth;

import java.util.Optional;
import java.util.UUID;

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

    public boolean verifyPassword(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword);
    }

    public String login(String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = existingUser.get();

        if (!verifyPassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public User createUserAccount(String email, Role role) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("An account with this email already exists: " + email);
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setRole(role);
        newUser.setEnabled(false);
        newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

        return userRepository.save(newUser);
    }
}