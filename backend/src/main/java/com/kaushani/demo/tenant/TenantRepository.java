package com.kaushani.demo.tenant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushani.demo.room.Room;


public interface TenantRepository extends JpaRepository<Tenant, Long>  {

    Optional<Tenant> findByUser_Email(String email);

    List<Tenant> findByRoom(Room room);




    
}
