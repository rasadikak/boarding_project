package com.kaushani.demo.notifications;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}