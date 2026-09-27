package com.kaushani.demo.auth.dto;

public class SendMail {

    private String token;
    
    private String email;

    public SendMail(){}

    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
}
