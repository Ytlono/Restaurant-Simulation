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

    public KitchenTicketEntity getByActor(CookEntity actor) {
        return ticketRepository.getByActor(actor);
    }

    @Transactional
    public void updateTicketsStatusForActor(CookEntity actor, OrderTicketStatus newStatus) {
        int updated = ticketRepository.updateStatusByActorAndCurrentStatus(
                actor,
                OrderTicketStatus.IN_PROGRESS,
                newStatus
        );
        log.info("Updated {} IN_PROGRESS tickets for cook {} to {}", updated, actor.getId(), newStatus);
    }
}
