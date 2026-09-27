package com.kaushani.demo.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaushani.demo.auth.dto.LoginRequest;
import com.kaushani.demo.auth.dto.ForgotPasswordRequest;
import com.kaushani.demo.auth.dto.ResetPasswordRequest;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordResetService passwordResetService;

    public AuthController(AuthService authService,UserRepository userRepository,PasswordResetService passwordResetService){
        this.authService=authService;
        this.userRepository=userRepository;
        this.passwordResetService=passwordResetService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        
        
        return authService.login(loginRequest.getEmail(),loginRequest.getPassword());
    }


    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        passwordResetService.sendPasswordSetupEmail(user);
        return "Password reset email sent";
    }

    @PostMapping("/reset-password")
        public String resetPassword(@RequestBody ResetPasswordRequest request) {
            passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
            return "Password has been reset";
        }
    
    
}
