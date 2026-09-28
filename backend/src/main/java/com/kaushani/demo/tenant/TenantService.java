package com.kaushani.demo.tenant;

import com.kaushani.demo.auth.UserRepository;
import java.security.Timestamp;
import java.util.List;


import org.springframework.stereotype.Service;

import com.kaushani.demo.auth.AuthService;
import com.kaushani.demo.auth.PasswordResetService;
import com.kaushani.demo.auth.Role;
import com.kaushani.demo.auth.User;
import com.kaushani.demo.room.Room;
import com.kaushani.demo.room.RoomService;

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

    public void updateTenant(){

    }

    public void disableTenant(){}

    public List <Tenant> getAllTenants(){

        return tenantRepository.findAll();
    }

    public List <Tenant>  getActiveTenants(User user){

        return userRepository.findAll(user.getEnabled());

        
    }

    public Tenant getTenantById(Long id){

        return tenantRepository.findById(id).orElseThrow(()-> new RuntimeException("tenant" + id + "not found"));
    }

    public void reassignRoom(){}

    public void getMyProfile(String email){}

    public void getTenantsByRoom(){}
    
}
