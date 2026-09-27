package com.kaushani.demo.room;

import java.sql.Timestamp;

import jakarta.persistence.*;


@Entity
@Table(name="rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String roomNumber;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private RoomType type;


    @Column(nullable=false)
    private int capacity;

    @Column(nullable = false)
    private int rentAmount;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(nullable=false)
    private Timestamp createdAt;

    public Room(){}

    public Room( String roomNumber, RoomType type, int capacity, RoomStatus status, int rentAmount) {
        
        this.roomNumber=roomNumber;
        this.type=type;
        this.capacity=capacity;
        this.status=status;
        this.rentAmount= rentAmount;
        

        }

    public Long getId() { return id; }
    public String getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public int getCapacity(){return capacity;}
    public RoomStatus getStatus(){return status;}
    public int getRentAmount(){return rentAmount;}
    public Timestamp getCreatedAt(){return createdAt;}

    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setType(RoomType type) { this.type=type; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setStatus(RoomStatus status){this.status=status;}
    public void setRentAmount(int rentAmount){this.rentAmount=rentAmount;}
    public void setCreatedAt(Timestamp createdAt){this.createdAt=createdAt;}

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    
}
