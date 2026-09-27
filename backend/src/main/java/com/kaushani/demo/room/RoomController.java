package com.kaushani.demo.room;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController 
@RequestMapping("/api/room")
public class RoomController {

    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public RoomController(RoomRepository roomRepository,RoomService roomService){
        this.roomRepository= roomRepository;
        this.roomService=roomService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public List<Room> getAllRooms() {
        return roomService.getRooms();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{roomNumber}")
    public Room getRoomByNumber(@PathVariable String roomNumber) {
        return roomService.getRoomByNumber(roomNumber);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room.getRoomNumber(), room.getType(), room.getCapacity(), room.getStatus(), room.getRentAmount());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{roomNumber}")
    public Room updateRoom(@PathVariable String roomNumber, @RequestBody Room room) {
        return roomService.updateRoom(roomNumber, room.getType(), room.getCapacity(), room.getStatus(), room.getRentAmount());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{roomNumber}")
    public String deleteRoom(@PathVariable String roomNumber){
        return roomService.deleteRoom(roomNumber);
    }
    
    
    
    
}
