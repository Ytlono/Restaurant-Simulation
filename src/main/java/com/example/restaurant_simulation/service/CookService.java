package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import com.example.restaurant_simulation.enums.CookStatus;
import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.CookEntity;
import com.example.restaurant_simulation.model.entity.KitchenTicketEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.repository.CookRepository;
import com.example.restaurant_simulation.service.interfaces.ActorServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CookService implements ActorServiceInterface<CookEntity,CookStatus> {
    private final CookRepository cookRepository;
    private final OrderTicketService ticketService;
    private final KitchenTicketService kitchenTicketService;
    private final RestaurantServiceProperties properties;

    @Transactional
    public void processOrder(CookEntity cook) {
        KitchenTicketEntity orderTicket = kitchenTicketService.getOldestTicketByTypeAndStatus(
                TicketType.KITCHEN,
                OrderTicketStatus.PENDING
        );

        if (orderTicket == null)
            return;

        cook.setCurrentTicket(orderTicket);
        cook.setStatus(CookStatus.PROCESSING);
        orderTicket.setActor(cook);

        ticketService.updateStatus(
                orderTicket.getId(),
                OrderTicketStatus.IN_PROGRESS
        );

        cookRepository.save(cook);
    }

    @Override
    public CookEntity getAvailable(){
        return cookRepository.findFirstByStatusOrderByUpdatedAtAsc(
                CookStatus.AVAILABLE
        );
    }

    @Override
    @Transactional
    public void checkAndUpdateAvailability(CookEntity cook) {
        Instant threshold = Instant.now().minus(
                properties.getActors().getCookActorThreshold()
        );

        if (!threshold.isBefore(cook.getUpdatedAt())) return;

        Long ticketId;

        if (cook.getCurrentTicket() != null) {
            ticketId = cook.getCurrentTicket().getId();
        } else {
            KitchenTicketEntity ticket = kitchenTicketService.getByActor(cook);

            if (ticket == null) return;
            ticketId = ticket.getId();
        }

        updateStatus(cook.getId(),CookStatus.AVAILABLE);
        ticketService.updateStatus(ticketId, OrderTicketStatus.COMPLETED);
    }

    @Override
    public void updateStatus(Long id,CookStatus status){
        cookRepository.updateStatus(id,status);
    }
}
