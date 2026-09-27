package com.kaushani.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("admin123 -> " + encoder.encode("12345"));
        
    }
}