package com.kaushani.demo.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByTenantId(Long tenantId);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByTenantIdAndMonth(Long tenantId, String month);

    List<Payment> findByMonthAndStatus(String month, PaymentStatus status);
}