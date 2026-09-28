package com.kaushani.demo.tenant.dto;

import java.sql.Timestamp;

public class CreateTenantRequest {

    private String name;
    private String email;
    private String contactNumber;
    private String guardianInfo;
    private Timestamp moveInDate;
    private String roomNumber;

    public CreateTenantRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getGuardianInfo() { return guardianInfo; }
    public void setGuardianInfo(String guardianInfo) { this.guardianInfo = guardianInfo; }

    public Timestamp getMoveInDate() { return moveInDate; }
    public void setMoveInDate(Timestamp moveInDate) { this.moveInDate = moveInDate; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
}