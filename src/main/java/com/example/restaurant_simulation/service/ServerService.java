package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.dto.event.OrderReadyEvent;
import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.KitchenTicketEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerService {
    private final KitchenTicketService kitchenTicketService;
    private final OrderTicketService orderTicketService;
    private final OrderEventPublisher orderEventPublisher;

    @Transactional
    public void processOrder(){
        KitchenTicketEntity orderTicket = kitchenTicketService.getOldestTicketByTypeAndStatus(
                TicketType.KITCHEN,
                    OrderTicketStatus.COMPLETED
        );

        if (orderTicket == null) {
            System.out.println("I CANT SEEE THEM");
            return;
        }

        orderTicketService.updateStatus(
                orderTicket.getId(),
                OrderTicketStatus.PROCESSED
        );

        OrderReadyEvent event = OrderReadyEvent.builder()
                .orderId(orderTicket.getOrder().getId())
                .build();

        orderEventPublisher.publish(event);
    }
}
