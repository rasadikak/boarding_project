package com.kaushani.demo.tenant;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController 
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    public TenantController( TenantService tenantService, TenantRepository tenantRepository){
        this.tenantService=tenantService;
        this.tenantRepository=tenantRepository;
    }

    @GetMapping("/all")
    public List <Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }

    @GetMapping("/active")
    public List<Tenant> getActiveTenants() {
        return tenantService.getActiveTenants();
    }

    @GetMapping("/{id}")
    public  Tenant getTenantById(Long id) {
        return tenantService.getTenantById(id);
    }

    @GetMapping("/profile/{email}")
    public Tenant getMyProfile(String email) {
        return tenantService.getMyProfile(email)
    }

    @GetMapping("/tenants/{roomNumber}")
    public  List<Tenant> getTenantsByRoom(String roomNumber) {
        return tenantService.getTenantsByRoom(roomNumber);
    }
    

    @PostMapping("/")
    public Tenant createTenant(String name, String email, String contactNumber,
                               String guardianInfo, Timestamp moveInDate, String roomNumber) {
        
        return tenantService.createTenant(name,email,contactNumber,guardianInfo,moveInDate,roomNumber);
    }

    @PutMapping("/{id}")
    public Tenant updateTenant(Long id, String name, String contactNumber, String guardianInfo){
        
        
        return tenantService.updateTenant( id,  name, contactNumber,  guardianInfo)
    }

    @PutMapping("/disable/{id}")
    public void disableTenant(Long id) {
        
        
         tenantService.disableTenant(id);
    }

    @PutMapping("reassign/{id}")
    public Tenant reassignRoom(Long id, String newRoomNumber) {
        
        
        return tenantService.reassignRoom(id, newRoomNumber);
    }
    
    
    
    
    


    
}
