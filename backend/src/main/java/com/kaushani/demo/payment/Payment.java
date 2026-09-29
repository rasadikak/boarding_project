package com.kaushani.demo.payment;

import java.sql.Timestamp;

import com.kaushani.demo.tenant.Tenant;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private Timestamp dueDate;

    @Column(nullable = true)
    private Timestamp paidDate;

    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    public Payment() {}

    public Payment(int amount, Timestamp dueDate, String month, PaymentStatus status, Tenant tenant) {
        this.amount = amount;
        this.dueDate = dueDate;
        this.month = month;
        this.status = status;
        this.tenant = tenant;
    }

    public Long getId() { return id; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public Timestamp getDueDate() { return dueDate; }
    public void setDueDate(Timestamp dueDate) { this.dueDate = dueDate; }

    public Timestamp getPaidDate() { return paidDate; }
    public void setPaidDate(Timestamp paidDate) { this.paidDate = paidDate; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}