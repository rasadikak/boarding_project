package com.kaushani.demo.complaint;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaushani.demo.complaint.dto.CreateComplaintRequest;
import com.kaushani.demo.complaint.dto.UpdateComplaintStatusRequest;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // ---------- Tenant only ----------

    @PreAuthorize("hasRole('TENANT')")
    @PostMapping
    public Complaint submitComplaint(Authentication authentication, @RequestBody CreateComplaintRequest request) {
        return complaintService.submitComplaint(authentication.getName(), request.getDescription());
    }

    @PreAuthorize("hasRole('TENANT')")
    @GetMapping("/me")
    public List<Complaint> getMyComplaints(Authentication authentication) {
        return complaintService.getMyComplaints(authentication.getName());
    }

    // ---------- Admin only ----------

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    public List<Complaint> getComplaintsByStatus(@PathVariable ComplaintStatus status) {
        return complaintService.getComplaintsByStatus(status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable Long id, @RequestBody UpdateComplaintStatusRequest request) {
        return complaintService.updateStatus(id, request.getStatus());
    }
}