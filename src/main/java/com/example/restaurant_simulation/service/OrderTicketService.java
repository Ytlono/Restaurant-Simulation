package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.*;
import com.example.restaurant_simulation.model.repository.OrderTicketRepository;
import com.example.restaurant_simulation.model.repository.ServiceTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderTicketService {
    private  final OrderTicketRepository orderTicketRepository;
    private final ServiceTicketRepository serviceTicketRepository;

    public OrderTicketEntity createOrderTicketByType(OrderEntity order, TicketType type) {
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

        return orderTicketRepository.saveAndFlush(ticketEntity);
    }

    public void setActorToKitchenTicket(KitchenTicketEntity ticket, CookEntity actor) {
        ticket.setActor(actor);
        orderTicketRepository.save(ticket);
    }


    public void updateStatus(Long id, OrderTicketStatus status){
        orderTicketRepository.updateStatus(id,status);
    }

    public OrderTicketEntity getOldestTicketByTypeAndStatus(TicketType ticketType, OrderTicketStatus orderTicketStatus) {
        return orderTicketRepository.findFirstByTypeAndStatusOrderByCreatedAtAsc(ticketType,orderTicketStatus);
    }

    public ServiceTicketEntity findById(Long ticketId) {
        return (ServiceTicketEntity) orderTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
    }

    public ServiceTicketEntity findServiceTicketByOrderId(Long orderId){
        return serviceTicketRepository.findByOrderId(orderId).orElseThrow();
    }
}
