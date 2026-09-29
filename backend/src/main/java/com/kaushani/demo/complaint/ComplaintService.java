package com.kaushani.demo.complaint;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaushani.demo.tenant.Tenant;
import com.kaushani.demo.tenant.TenantService;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final TenantService tenantService;

    public ComplaintService(ComplaintRepository complaintRepository, TenantService tenantService) {
        this.complaintRepository = complaintRepository;
        this.tenantService = tenantService;
    }

    @Transactional
    public Complaint submitComplaint(String email, String description) {

        Tenant tenant = tenantService.getMyProfile(email);

        Complaint complaint = new Complaint(description, tenant);
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getMyComplaints(String email) {
        Tenant tenant = tenantService.getMyProfile(email);
        return complaintRepository.findByTenantId(tenant.getId());
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getComplaintsByStatus(ComplaintStatus status) {
        return complaintRepository.findByStatus(status);
    }

    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint " + id + " not found"));
    }

    @Transactional
    public Complaint updateStatus(Long id, ComplaintStatus newStatus) {
        Complaint complaint = getComplaintById(id);
        complaint.setStatus(newStatus);
        return complaintRepository.save(complaint);
    }
}