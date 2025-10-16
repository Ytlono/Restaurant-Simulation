package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.KitchenTicketEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.entity.SimulatedActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenTicketRepository extends JpaRepository<KitchenTicketEntity,Long> {
    KitchenTicketEntity findFirstByTypeAndStatusOrderByCreatedAtAsc(TicketType type, OrderTicketStatus status);

    KitchenTicketEntity getBySimulatedActor(SimulatedActorEntity actor);
}
