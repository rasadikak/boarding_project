package com.kaushani.demo.tenant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaushani.demo.room.Room;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findByRoom(Room room);

    Optional<Tenant> findByUser_Email(String email);

    List<Tenant> findByUser_EnabledTrue();

    long countByRoomAndMoveOutDateIsNull(Room room);
}