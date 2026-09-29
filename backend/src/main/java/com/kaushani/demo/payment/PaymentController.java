package com.kaushani.demo.payment;

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

import com.kaushani.demo.payment.dto.CreatePaymentRequest;
import com.kaushani.demo.payment.dto.CreatePaymentsForAllRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    

    @PreAuthorize("hasRole('TENANT')")
    @GetMapping("/me")
    public List<Payment> getMyPayments(Authentication authentication) {
        return paymentService.getMyPayments(authentication.getName());
    }

    

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Payment createPayment(@RequestBody CreatePaymentRequest request) {
        return paymentService.createPayment(request.getTenantId(), request.getMonth(), request.getDueDate());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate-monthly")
    public String createPaymentsForAllActiveTenants(@RequestBody CreatePaymentsForAllRequest request) {
        paymentService.createPaymentsForAllActiveTenants(request.getMonth(), request.getDueDate());
        return "Payments generated for " + request.getMonth();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/mark-paid")
    public Payment markAsPaid(@PathVariable Long id) {
        return paymentService.markAsPaid(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tenant/{tenantId}")
    public List<Payment> getPaymentsByTenant(@PathVariable Long tenantId) {
        return paymentService.getPaymentsByTenant(tenantId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        return paymentService.getPaymentsByStatus(status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/refresh-overdue")
    public String refreshOverdueStatus() {
        paymentService.refreshOverdueStatus();
        return "Overdue statuses refreshed";
    }
}