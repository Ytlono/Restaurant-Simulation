package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.model.entity.ServiceTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServiceTicketRepository extends JpaRepository<ServiceTicketEntity, Long> {

    @Query("SELECT st FROM ServiceTicketEntity st WHERE st.order.id = :orderId")
    Optional<ServiceTicketEntity> findByOrderId(@Param("orderId") Long orderId);

}