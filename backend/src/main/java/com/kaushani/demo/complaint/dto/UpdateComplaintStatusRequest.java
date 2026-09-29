package com.kaushani.demo.complaint.dto;

import com.kaushani.demo.complaint.ComplaintStatus;

public class UpdateComplaintStatusRequest {

    private ComplaintStatus status;

    public UpdateComplaintStatusRequest() {}

    public ComplaintStatus getStatus() { return status; }
    public void setStatus(ComplaintStatus status) { this.status = status; }
}