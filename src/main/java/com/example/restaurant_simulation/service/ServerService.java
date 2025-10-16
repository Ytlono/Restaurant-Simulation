package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.dto.event.OrderReadyEvent;
import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.repository.ServerRepository;
import com.example.restaurant_simulation.producer.MessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerService {
    private final OrderTicketService ticketService;
    private final OrderEventPublisher orderEventPublisher;

    @Transactional
    public void processOrder(){
        OrderTicketEntity orderTicket = ticketService.getOldestTicketByTypeAndStatus(
                TicketType.KITCHEN,
                    OrderTicketStatus.COMPLETED
        );

        if (orderTicket == null)
            return;

        ticketService.updateStatus(
                orderTicket.getId(),
                OrderTicketStatus.PROCESSED
        );

        OrderReadyEvent event = OrderReadyEvent.builder()
                .orderId(orderTicket.getId())
                .build();

        orderEventPublisher.publish(event);
    }
}
