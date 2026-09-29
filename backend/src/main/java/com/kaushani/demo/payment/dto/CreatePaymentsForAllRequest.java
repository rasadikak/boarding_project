package com.kaushani.demo.payment.dto;

import java.sql.Timestamp;

public class CreatePaymentsForAllRequest {

    private String month;
    private Timestamp dueDate;

    public CreatePaymentsForAllRequest() {}

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Timestamp getDueDate() { return dueDate; }
    public void setDueDate(Timestamp dueDate) { this.dueDate = dueDate; }
}