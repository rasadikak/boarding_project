package com.kaushani.demo.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long>{

    List <Payment> findByTenant_id(Long tenant_id);

    List <Payment> findBystatus(PaymentStatus status);

    

    
}
