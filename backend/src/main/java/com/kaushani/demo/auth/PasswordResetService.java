package com.kaushani.demo.auth;

import com.kaushani.demo.notifications.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final PasswordSetupTokenRepository tokenRepository;
    private final EmailService emailService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public PasswordResetService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtil jwtUtil,
                                 PasswordSetupTokenRepository tokenRepository,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public void sendPasswordSetupEmail(User user) {

        List<PasswordSetupToken> oldTokens = tokenRepository.findByUserAndUsedFalse(user);
        for (PasswordSetupToken old : oldTokens) {
            old.setUsed(true);
        }
        tokenRepository.saveAll(oldTokens);

        String token = jwtUtil.generatePasswordSetupToken(user.getEmail());
        tokenRepository.save(new PasswordSetupToken(token, user));

        String link = frontendUrl + "/set-password?token=" + token;

        emailService.sendEmail(
                user.getEmail(),
                "Set up your password",
                "Click here to set your password: " + link
        );
    }

    public void resetPassword(String token, String newPassword) {

        String email = jwtUtil.extractPasswordSetupEmail(token);

        PasswordSetupToken setupToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or unknown token"));

        if (Boolean.TRUE.equals(setupToken.getUsed())) {
            throw new RuntimeException("This link has already been used");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setEnabled(true);
        userRepository.save(user);

        setupToken.setUsed(true);
        tokenRepository.save(setupToken);

        emailService.sendEmail(
                user.getEmail(),
                "Your password was changed",
                "This is a confirmation that your account password was just changed. " +
                "If you didn't do this, please contact support immediately."
        );
    }
}