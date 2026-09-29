package com.kaushani.demo.notifications;

import java.sql.Timestamp;

import com.kaushani.demo.tenant.Tenant;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = true)
    private Tenant tenant;

    @Column(nullable = false)
    private Timestamp sentAt;

    public Notification() {}

    public Notification(String title, String message, NotificationType type, Tenant tenant) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.tenant = tenant;
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Timestamp getSentAt() { return sentAt; }

    @PrePersist
    protected void onCreate() {
        this.sentAt = new Timestamp(System.currentTimeMillis());
    }
}