package com.kaushani.demo.auth.dto;

public class ResetPasswordRequest {

    private String token;
    private String password;
    private String email;

    public ResetPasswordRequest(){}

    
    public String getNewPassword() { return password; }
    public void setNewPassword(String password) { this.password = password; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
}
