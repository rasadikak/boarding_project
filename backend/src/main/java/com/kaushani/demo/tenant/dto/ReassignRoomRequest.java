package com.kaushani.demo.tenant.dto;

public class ReassignRoomRequest {

    private String newRoomNumber;

    public ReassignRoomRequest() {}

    public String getNewRoomNumber() { return newRoomNumber; }
    public void setNewRoomNumber(String newRoomNumber) { this.newRoomNumber = newRoomNumber; }
}