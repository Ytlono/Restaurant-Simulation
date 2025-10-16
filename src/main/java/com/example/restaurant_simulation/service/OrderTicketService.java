package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.*;
import com.example.restaurant_simulation.model.repository.OrderTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderTicketService {
    private  final OrderTicketRepository orderTicketRepository;

    public void createOrderTicketByType(OrderEntity order, TicketType type) {
        OrderTicketEntity ticketEntity;

        switch (type) {
            case KITCHEN -> ticketEntity = KitchenTicketEntity.builder()
                    .order(order)
                    .status(OrderTicketStatus.PENDING)
                    .type(type)
                    .build();

            case SERVICE -> ticketEntity = ServiceTicketEntity.builder()
                    .order(order)
                    .status(OrderTicketStatus.PENDING)
                    .type(type)
                    .build();

            default -> throw new IllegalArgumentException("Unsupported ticket type: " + type);
        }

        orderTicketRepository.save(ticketEntity);
        log.info("Created {} ticket for order {}", type, order.getId());
    }

    public void setActorToKitchenTicket(KitchenTicketEntity ticket, CookEntity actor) {
        ticket.setActor(actor);
        orderTicketRepository.save(ticket);
    }


    public void updateStatus(Long id, OrderTicketStatus status){
        orderTicketRepository.updateStatus(id,status);
    }
}
