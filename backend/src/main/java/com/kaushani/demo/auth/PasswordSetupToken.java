package com.kaushani.demo.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordSetupToken {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public PasswordSetupToken(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;


    }

    public String forgetPasswordChange(String Password){

    }

    public String forcePasswordChange(String Password){

    }

    
}
