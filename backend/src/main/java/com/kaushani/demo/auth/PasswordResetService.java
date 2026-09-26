package com.kaushani.demo.auth;

import com.kaushani.demo.notifications.EmailService;
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

        // Invalidate any older, unused links for this user first
        List<PasswordSetupToken> oldTokens = tokenRepository.findByUserAndUsedFalse(user);
        for (PasswordSetupToken old : oldTokens) {
            old.setUsed(true);
        }
        tokenRepository.saveAll(oldTokens);

        String token = jwtUtil.generatePasswordSetupToken(user.getEmail());
        tokenRepository.save(new PasswordSetupToken(token, user));

        String link = "https://yourfrontend.com/set-password?token=" + token;

        emailService.sendEmail(
                user.getEmail(),
                "Set up your password",
                "Click here to set your password: " + link
        );
    }

    public void resetPassword(String token, String newPassword) {

        // 1. Verify signature, expiry, and purpose
        String email = jwtUtil.extractPasswordSetupEmail(token);

        // 2. Confirm this exact token hasn't already been used
        PasswordSetupToken setupToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or unknown token"));

        if (Boolean.TRUE.equals(setupToken.getUsed())) {
            throw new RuntimeException("This link has already been used");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Update the password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setEnabled(true);
        userRepository.save(user);

        // 4. Mark this token as used so it can't be redeemed again
        setupToken.setUsed(true);
        tokenRepository.save(setupToken);

        // 5. Notify the user their password was changed
        emailService.sendEmail(
                user.getEmail(),
                "Your password was changed",
                "This is a confirmation that your account password was just changed. " +
                "If you didn't do this, please contact support immediately."
        );
    }
}