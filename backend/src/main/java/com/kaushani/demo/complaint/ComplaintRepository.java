package com.kaushani.demo.complaint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByTenantId(Long tenantId);

    List<Complaint> findByStatus(ComplaintStatus status);
}