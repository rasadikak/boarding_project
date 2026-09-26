package com.kaushani.demo.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecretString;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private static final long PASSWORD_SETUP_EXPIRATION = 1000L * 60 * 60 * 24; // 24 hours

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes(StandardCharsets.UTF_8));
    }

    //  Login tokens 

    public String generateToken(String email, Role role) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name());
        claims.put("purpose", "login");

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public Role extractRole(String token) {
        Claims claims = parseClaims(token);
        String roleString = claims.get("role", String.class);
        return Role.valueOf(roleString);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseClaims(token);
            String purpose = claims.get("purpose", String.class);
            return "login".equals(purpose);
        } catch (Exception e) {
            return false;
        }
    }

    //  Password setup / reset tokens 

    public String generatePasswordSetupToken(String email) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + PASSWORD_SETUP_EXPIRATION);

        Map<String, Object> claims = new HashMap<>();
        claims.put("purpose", "password_setup");

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String extractPasswordSetupEmail(String token) {
        Claims claims = parseClaims(token);

        String purpose = claims.get("purpose", String.class);
        if (!"password_setup".equals(purpose)) {
            throw new RuntimeException("Invalid token purpose");
        }

        return claims.getSubject();
    }

    

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}