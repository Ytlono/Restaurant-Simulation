package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.enums.CookStatus;
import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.enums.OrderStatus;
import com.example.restaurant_simulation.model.entity.CookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface CookRepository extends JpaRepository<CookEntity,Long> {
    CookEntity findFirstByStatusOrderByUpdatedAtAsc(CookStatus customerStatus);

    List<CookEntity> findAllByUpdatedAtBefore(Instant threshold);

}
