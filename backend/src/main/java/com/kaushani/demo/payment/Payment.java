package com.kaushani.demo.payment;

import java.sql.Timestamp;

import com.kaushani.demo.auth.User;
import com.kaushani.demo.payment.PaymentStatus;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name= "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long amount;

    @Column(nullable = true )
    private Timestamp dueDate;

    @Column(nullable=true)
    private Timestamp paidDate;

    @Column(nullable = false)
    private String month;

    @Column(nullable=false)
    private PaymentStatus status;

    @Column(nullable=false)
    private Timestamp createdAt;

    @ManyToOne 
    @JoinColumn (name="user_id", nullable=false)
    private User user;

    public Payment(Long id,Long amount,Timestamp dueDate,Timestamp paidDate,String month,PaymentStatus status,Timestamp createdAt,User user){
        this.id=id;
        this.amount=amount;
        this.dueDate= dueDate;
        this.paidDate=paidDate;
        this.month=month;
        this.status= status;
        this.user=user;
    }

    public Long getId(Long id){return id;}
    public Long getAmount(Long amount){return amount;}
    public Timestamp getDueDate(Timestamp dueDate){return dueDate;}
    public Timestamp getPaidDate(Timestamp paidDate){return paidDate;}
    public String getMonth(String month){return month;}
    public User getUser(User user){return user;}

    public void setId(Long id){ this.id=id;}
    public void setAmount(Long amount){this.amount=amount;}
    public void setDueDate(Timestamp dueDate){this.dueDate=dueDate;}
    public void setPaidDate(Timestamp paidDate){this.paidDate=paidDate;}
    public void setMonth(String month){this.month=month;}
    public void setUser(User user){this.user=user;}


    











    
}
