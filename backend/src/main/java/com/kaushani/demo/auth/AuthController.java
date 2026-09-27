package com.kaushani.demo.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaushani.demo.auth.dto.LoginRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        
        
        return authService.login(loginRequest.getEmail(),loginRequest.getPassword());
    }
    
    
}
