package com.kaushani.demo.room;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomNumber));
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(String roomNumber, RoomType type, int capacity, RoomStatus status, int rentAmount) {

        boolean isExisting = roomRepository.existsByRoomNumber(roomNumber);
        if (isExisting) {
            throw new RuntimeException(roomNumber + " room already exists");
        }

        Room newRoom = new Room(roomNumber, type, capacity, status, rentAmount);
        return roomRepository.save(newRoom);
    }

    public Room updateRoom(String roomNumber, RoomType type, int capacity, RoomStatus status, int rentAmount) {

        Room existingRoom = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomNumber));

        existingRoom.setType(type);
        existingRoom.setCapacity(capacity);
        existingRoom.setStatus(status);
        existingRoom.setRentAmount(rentAmount);

        return roomRepository.save(existingRoom);
    }

    public String deleteRoom(String roomNumber) {

        Room existingRoom = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomNumber));

        roomRepository.delete(existingRoom);

        return "Room " + roomNumber + " deleted successfully";
    }

    public void refreshOccupancy(Room room, long activeTenantCount) {

        if (room.getStatus() == RoomStatus.MAINTENANCE) {
            return;
        }

        RoomStatus newStatus = activeTenantCount > 0 ? RoomStatus.OCCUPIED : RoomStatus.VACANT;

        if (room.getStatus() != newStatus) {
            room.setStatus(newStatus);
            roomRepository.save(room);
        }
    }
}