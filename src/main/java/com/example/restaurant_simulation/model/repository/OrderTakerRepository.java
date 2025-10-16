package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.enums.OrderTakerStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderTakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderTakerRepository extends JpaRepository<OrderTakerEntity,Long> {
    OrderTakerEntity findFirstByStatusOrderByUpdatedAtAsc(OrderTakerStatus status);

    @Modifying
    @Query("UPDATE OrderTakerEntity n SET n.status = :status WHERE n.id = :id")
    void updateStatus(Long id, OrderTakerStatus status);
}
