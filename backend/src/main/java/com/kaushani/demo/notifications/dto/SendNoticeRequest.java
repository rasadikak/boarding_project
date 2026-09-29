package com.kaushani.demo.notifications.dto;

public class SendNoticeRequest {

    private String title;
    private String message;

    public SendNoticeRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}