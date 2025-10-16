package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.enums.CookStatus;
import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.enums.OrderStatus;
import com.example.restaurant_simulation.model.entity.CookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CookRepository extends JpaRepository<CookEntity,Long> {
    CookEntity findFirstByStatusOrderByUpdatedAtAsc(CookStatus customerStatus);

    @Modifying
    @Query("UPDATE CookEntity n SET n.status = :status WHERE n.id = :id")
    void updateStatus(Long id, CookStatus status);
}
