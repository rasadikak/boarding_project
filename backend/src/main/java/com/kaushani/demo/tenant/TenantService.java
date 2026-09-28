package com.kaushani.demo.tenant;

import com.kaushani.demo.auth.UserRepository;
import java.sql.Timestamp;
import java.util.List;


import org.springframework.stereotype.Service;

import com.kaushani.demo.auth.AuthService;
import com.kaushani.demo.auth.PasswordResetService;
import com.kaushani.demo.auth.Role;
import com.kaushani.demo.auth.User;
import com.kaushani.demo.room.Room;
import com.kaushani.demo.room.RoomService;
import com.kaushani.demo.room.RoomStatus;

import jakarta.transaction.Transactional;

@Service
public class TenantService {


    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final AuthService authService;
    private final PasswordResetService passwordResetService;
    private final RoomService roomService;

    public TenantService(TenantRepository tenantRepository,
                         AuthService authService,
                         PasswordResetService passwordResetService,
                         RoomService roomService, UserRepository userRepository) {
        this.tenantRepository = tenantRepository;
        this.authService = authService;
        this.passwordResetService = passwordResetService;
        this.roomService = roomService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Tenant createTenant(String name, String email, String contactNumber,
                               String guardianInfo, Timestamp moveInDate, String roomNumber) {

        Room room = roomService.getRoomByNumber(roomNumber);

        User user = authService.createUserAccount(email, Role.TENANT);

        Tenant newTenant = new Tenant(name, contactNumber, guardianInfo, moveInDate, user, room);
        Tenant savedTenant = tenantRepository.save(newTenant);

        passwordResetService.sendPasswordSetupEmail(user);

        return savedTenant;
    }

   

    public List <Tenant> getAllTenants(){

        return tenantRepository.findAll();
    }

    public List<Tenant> getActiveTenants() {

        return tenantRepository.findByUser_EnabledTrue();
    }

    public Tenant getTenantById(Long id){

        return tenantRepository.findById(id).orElseThrow(()-> new RuntimeException("tenant" + id + "not found"));
    }

    

    public Tenant getMyProfile(String email) {

        return tenantRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Tenant profile not found for: " + email));
    }

    public List<Tenant> getTenantsByRoom(String roomNumber) {

        Room room = roomService.getRoomByNumber(roomNumber);
        return tenantRepository.findByRoom(room);
    }

    @Transactional
    public Tenant updateTenant(Long id, String name, String contactNumber, String guardianInfo) {

        Tenant tenant = getTenantById(id);

        tenant.setName(name);
        tenant.setContactNumber(contactNumber);
        tenant.setGuardianInfo(guardianInfo);

        return tenantRepository.save(tenant);
    }

    @Transactional
    public void disableTenant(Long id) {

        Tenant tenant = getTenantById(id);

        if (tenant.getMoveOutDate() != null) {
            throw new RuntimeException("Tenant " + id + " has already moved out");
        }

        tenant.setMoveOutDate(new Timestamp(System.currentTimeMillis()));
        authService.disableUserAccount(tenant.getUser());

        tenantRepository.save(tenant);
    }

    @Transactional
    public Tenant reassignRoom(Long id, String newRoomNumber) {

        Tenant tenant = getTenantById(id);

        if (tenant.getMoveOutDate() != null) {
            throw new RuntimeException("Tenant " + id + " has already moved out");
        }

        Room newRoom = roomService.getRoomByNumber(newRoomNumber);

        if (newRoom.getStatus() == RoomStatus.MAINTENANCE) {
            throw new RuntimeException("Room " + newRoomNumber + " is under maintenance");
        }

        if (tenantRepository.countByRoomAndMoveOutDateIsNull(newRoom) >= newRoom.getCapacity()) {
            throw new RuntimeException("Room " + newRoomNumber + " is full");
        }

        tenant.setRoom(newRoom);
        return tenantRepository.save(tenant);
    }
}
