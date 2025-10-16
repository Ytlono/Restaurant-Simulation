package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.KitchenTicketEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.entity.SimulatedActorEntity;
import com.example.restaurant_simulation.model.repository.KitchenTicketRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class KitchenTicketService {
    private final KitchenTicketRepository ticketRepository;;

    public KitchenTicketEntity getOldestTicketByTypeAndStatus(TicketType type, OrderTicketStatus status){
        return ticketRepository.findFirstByTypeAndStatusOrderByCreatedAtAsc(type,status);
    }

    public KitchenTicketEntity getByActor(SimulatedActorEntity actor) {
        return ticketRepository.getBySimulatedActor(actor);
    }
}
