package com.kaushani.demo.tenant;

import java.sql.Timestamp;

import com.kaushani.demo.auth.User;
import com.kaushani.demo.room.Room;


import jakarta.persistence.*;


@Entity
@Table(name="tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    


    @Column(nullable=false)
    private String contactNumber;

    @Column(nullable = true)
    private String guardianInfo;

    @Column(nullable=false)
    private Timestamp moveInDate;

    @Column(nullable=false)
    private Timestamp createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public Tenant(){}

    public Tenant( String name, String contactNumber, String guardianInfo, Timestamp moveInDate, User user, Room room) {
        
        this.name=name;
        this.contactNumber=contactNumber;
        
        this.guardianInfo=guardianInfo;
        this.moveInDate=moveInDate;

        this.user=user;
        this.room=room;
        

        }

    public String getName() { return name; }
    public String getContactNumber() { return contactNumber; }
    public String getGuardianInfo() { return guardianInfo; }
    public Timestamp getMoveInDate(){return moveInDate;}
    public Timestamp getCreatedAt(){return createdAt;}

    public void setName(String name) { this.name = name; }
    public void setContactNumber(String contactNumber) { this.contactNumber=contactNumber; }
    public void setGuardianInfo(String guardianInfo) { this.guardianInfo = guardianInfo; }
    public void setMoveInDate(Timestamp moveInDate){this.moveInDate=moveInDate;}
    public void setCreatedAt(Timestamp createdAt){this.createdAt=createdAt;}

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
        

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    
}
