package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import com.example.restaurant_simulation.enums.*;
import com.example.restaurant_simulation.model.entity.CookEntity;
import com.example.restaurant_simulation.model.entity.KitchenTicketEntity;
import com.example.restaurant_simulation.model.entity.OrderTakerEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.repository.CookRepository;
import com.example.restaurant_simulation.service.interfaces.ActorServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CookService implements ActorServiceInterface<CookEntity,CookStatus> {
    private final CookRepository cookRepository;
    private final OrderTicketService ticketService;
    private final KitchenTicketService kitchenTicketService;
    private final RestaurantServiceProperties properties;

    public List<CookEntity> getAllCooks() {
        return cookRepository.findAll();
    }

    public CookEntity getCookById(Long id) {
        return cookRepository.findById(id).orElseThrow();
    }

    public CookEntity addCook(CookEntity cook) {
        cook.setRole(ActorRole.COOK);
        cook.setStatus(CookStatus.AVAILABLE);
        return cookRepository.save(cook);
    }

    public void deleteCook(Long id) {
        cookRepository.deleteById(id);
    }

    @Transactional
    public void processOrder(CookEntity cook) {
        KitchenTicketEntity orderTicket = kitchenTicketService.getOldestTicketByTypeAndStatus(
                TicketType.KITCHEN,
                OrderTicketStatus.PENDING
        );

        if (orderTicket == null)
            return;

        cook.setTicket(orderTicket);
        cook.setStatus(CookStatus.PROCESSING);

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
    public void checkAndUpdateAvailability() {
        Instant threshold = Instant.now().minus(
                properties.getActors().getCookActorThreshold()
        );

        List<CookEntity> expiredCooks = cookRepository.findAllByUpdatedAtBefore(threshold);

        if (expiredCooks.isEmpty()){
            System.out.println("COOOOK ACTOR NO EXPIRED COOK");
            return;
        }

        expiredCooks.forEach(cook -> {
            cook.setStatus(CookStatus.AVAILABLE);
            kitchenTicketService.updateTicketsStatus(cook.getTicket(),OrderTicketStatus.COMPLETED);
            cookRepository.save(cook);
        });

        log.info("Updated {} expired cooks to AVAILABLE and their tickets to CANCELLED", expiredCooks.size());
    }

    @Override
    public void updateStatus(Long id, CookStatus status){
        CookEntity cook = cookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cook not found: " + id));

        cook.setStatus(status);
        cookRepository.save(cook);
    }
}