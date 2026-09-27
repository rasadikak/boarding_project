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
    public List<Room> getAllRooms(@RequestParam String param) {
        return roomService.getRooms()
    }

    @PreAuthorize("hasAnyRole('ADMIN','TENANT')")
    @GetMapping("/{roomNumber}")
    public Room getRoomByNumber(String roomNumber) {
        return roomService.getRoomByNumber(roomNumber);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String createRoom(@RequestBody String entity) {

        return roomService.createRoom(entity, null, 0, null, 0)
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("path/{id}")
    public String updateRoom(@PathVariable String id, @RequestBody String entity) {
        
        return roomService.updateRoom()
        
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("")
    public String deleteRoom(){
        return roomService.deleteRoom(null)
        
    }
    
    
    
    
}
