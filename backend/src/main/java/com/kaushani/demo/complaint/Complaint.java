package com.kaushani.demo.complaint;

import java.sql.Timestamp;

import com.kaushani.demo.tenant.Tenant;

import jakarta.persistence.*;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    @Column(nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    public Complaint() {}

    public Complaint(String description, Tenant tenant) {
        this.description = description;
        this.tenant = tenant;
        this.status = ComplaintStatus.OPEN;
    }

    public Long getId() { return id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ComplaintStatus getStatus() { return status; }
    public void setStatus(ComplaintStatus status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}