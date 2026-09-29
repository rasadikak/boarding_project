package com.kaushani.demo.auth;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;



import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @JsonIgnore 
    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable=false)
    private Boolean enabled;

    @Column(nullable=false)
    private Timestamp createdAt;

    public User(){}

    public User( String email, String password, Role role, Boolean enabled) {
        
        this.email=email;
        this.password=password;
        this.role=role;
        this.enabled=enabled;
        

        }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole(){return role;}
    public Boolean getEnabled(){return enabled;}
    public Timestamp getCreatedAt(){return createdAt;}

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password=password; }
    public void setRole(Role role) { this.role = role; }
    public void setEnabled(Boolean enabled){this.enabled=enabled;}
    public void setCreatedAt(Timestamp createdAt){this.createdAt=createdAt;}

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    

    



    
}
