package com.kaushani.demo.tenant.dto;

public class UpdateTenantRequest {

    private String name;
    private String contactNumber;
    private String guardianInfo;

    public UpdateTenantRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getGuardianInfo() { return guardianInfo; }
    public void setGuardianInfo(String guardianInfo) { this.guardianInfo = guardianInfo; }
}