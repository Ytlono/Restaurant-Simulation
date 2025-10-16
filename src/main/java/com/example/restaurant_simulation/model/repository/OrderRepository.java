package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.enums.OrderStatus;
import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    OrderEntity findFirstByStatusOrderByUpdatedAtAsc(OrderStatus status);

    OrderEntity findFirstByStatusOrderByUpdatedAtDesc(OrderStatus status);

    @Modifying
    @Query("UPDATE OrderEntity n SET n.status = :status WHERE n.id = :id")
    void updateStatus(Long id, OrderStatus status);
}
