package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.CookEntity;
import com.example.restaurant_simulation.model.entity.KitchenTicketEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.entity.SimulatedActorEntity;
import com.example.restaurant_simulation.model.repository.KitchenTicketRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class KitchenTicketService {
    private final KitchenTicketRepository ticketRepository;;

    public KitchenTicketEntity getOldestTicketByTypeAndStatus(TicketType type, OrderTicketStatus status){
        return ticketRepository.findFirstByTypeAndStatusOrderByCreatedAtAsc(type,status);
    }

    @Transactional
    public void updateTicketsStatus(KitchenTicketEntity ticket, OrderTicketStatus newStatus) {
        if (ticket == null) {
            log.warn("❌ Attempted to update status of null KitchenTicketEntity!");
            return;
        }
        ticket.setStatus(newStatus);
        ticketRepository.save(ticket);
    }

}
