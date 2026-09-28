package com.kaushani.demo.tenant;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository){
        this.tenantRepository=tenantRepository;
    }

    public  Tenant createTenant(){
        
    }

    public Tenant updateTenant(){

    }

    public Tenant disableTenant(){}

    public List <Tenant> getAllTenants(){

        return tenantRepository.findAll();
    }

    public List <Tenant> getActiveTenants(){

        
    }

    public Tenant getTenantById(Long id){

        return tenantRepository.findById(id).orElseThrow(()-> new RuntimeException("tenant" + id + "not found"));
    }

    public Tenant reassignRoom(){}

    public Tenant getMyProfile(String email){}

    public <List> Tenant getTenantsByRoom(){}
    
}
