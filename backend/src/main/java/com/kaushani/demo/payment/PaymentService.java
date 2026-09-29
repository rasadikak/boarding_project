package com.kaushani.demo.payment;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaushani.demo.tenant.Tenant;
import com.kaushani.demo.tenant.TenantService;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TenantService tenantService;

    public PaymentService(PaymentRepository paymentRepository, TenantService tenantService) {
        this.paymentRepository = paymentRepository;
        this.tenantService = tenantService;
    }

    @Transactional
    public Payment createPayment(Long tenantId, String month, Timestamp dueDate) {

        Tenant tenant = tenantService.getTenantById(tenantId);

        boolean alreadyExists = !paymentRepository.findByTenantIdAndMonth(tenantId, month).isEmpty();
        if (alreadyExists) {
            throw new RuntimeException("A payment for " + month + " already exists for this tenant");
        }

        int amount = tenant.getRoom().getRentAmount();

        Payment payment = new Payment(amount, dueDate, month, PaymentStatus.PENDING, tenant);

        return paymentRepository.save(payment);
    }

    @Transactional
    public void createPaymentsForAllActiveTenants(String month, Timestamp dueDate) {

        List<Tenant> activeTenants = tenantService.getActiveTenants();

        for (Tenant tenant : activeTenants) {
            boolean alreadyExists = !paymentRepository.findByTenantIdAndMonth(tenant.getId(), month).isEmpty();
            if (!alreadyExists) {
                createPayment(tenant.getId(), month, dueDate);
            }
        }
    }

    @Transactional
    public Payment markAsPaid(Long paymentId) {

        Payment payment = getPaymentById(paymentId);

        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Payment " + paymentId + " is already marked as paid");
        }

        payment.setPaidDate(new Timestamp(System.currentTimeMillis()));
        payment.setStatus(PaymentStatus.PAID);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment " + id + " not found"));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByTenant(Long tenantId) {
        return paymentRepository.findByTenantId(tenantId);
    }

    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    public List<Payment> getMyPayments(String email) {
        Tenant tenant = tenantService.getMyProfile(email);
        return paymentRepository.findByTenantId(tenant.getId());
    }

    @Transactional
    public void refreshOverdueStatus() {

        List<Payment> pendingPayments = paymentRepository.findByStatus(PaymentStatus.PENDING);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        for (Payment payment : pendingPayments) {
            if (payment.getDueDate().before(now)) {
                payment.setStatus(PaymentStatus.OVERDUE);
                paymentRepository.save(payment);
            }
        }
    }
}