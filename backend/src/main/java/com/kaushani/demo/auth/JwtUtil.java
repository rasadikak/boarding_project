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

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes(StandardCharsets.UTF_8));
    }




    public String generateToken(String email, Role role){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name());

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token){
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();   
}


   public Role extractRole(String token){

        Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        String roleString = claims.get("role", String.class);
        return Role.valueOf(roleString);
    }


    public boolean isTokenValid(String token){
        try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    


    
}
