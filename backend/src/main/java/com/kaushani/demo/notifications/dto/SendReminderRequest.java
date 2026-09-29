package com.kaushani.demo.notifications.dto;

public class SendReminderRequest {

    private Long tenantId;
    private String title;
    private String message;

    public SendReminderRequest() {}

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}