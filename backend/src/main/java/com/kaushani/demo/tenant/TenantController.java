package com.kaushani.demo.tenant;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaushani.demo.tenant.dto.CreateTenantRequest;
import com.kaushani.demo.tenant.dto.ReassignRoomRequest;
import com.kaushani.demo.tenant.dto.UpdateTenantRequest;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    

    @PreAuthorize("hasRole('TENANT')")
    @GetMapping("/me")
    public Tenant getMyProfile(Authentication authentication) {
        return tenantService.getMyProfile(authentication.getName());
    }

   

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/active")
    public List<Tenant> getActiveTenants() {
        return tenantService.getActiveTenants();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Tenant getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/room/{roomNumber}")
    public List<Tenant> getTenantsByRoom(@PathVariable String roomNumber) {
        return tenantService.getTenantsByRoom(roomNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Tenant createTenant(@RequestBody CreateTenantRequest request) {
        return tenantService.createTenant(
                request.getName(),
                request.getEmail(),
                request.getContactNumber(),
                request.getGuardianInfo(),
                request.getMoveInDate(),
                request.getRoomNumber());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Tenant updateTenant(@PathVariable Long id, @RequestBody UpdateTenantRequest request) {
        return tenantService.updateTenant(
                id,
                request.getName(),
                request.getContactNumber(),
                request.getGuardianInfo());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/move-out")
    public String moveOutTenant(@PathVariable Long id) {
        tenantService.disableTenant(id);
        return "Tenant " + id + " has moved out and the account is disabled";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reassign")
    public Tenant reassignRoom(@PathVariable Long id, @RequestBody ReassignRoomRequest request) {
        return tenantService.reassignRoom(id, request.getNewRoomNumber());
    }
}