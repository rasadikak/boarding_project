package com.kaushani.demo.payment.dto;

import java.sql.Timestamp;

public class CreatePaymentRequest {

    private Long tenantId;
    private String month;
    private Timestamp dueDate;

    public CreatePaymentRequest() {}

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Timestamp getDueDate() { return dueDate; }
    public void setDueDate(Timestamp dueDate) { this.dueDate = dueDate; }
}