package com.example.restaurant_simulation.consumer;

import com.example.restaurant_simulation.dto.event.OrderReadyEvent;
import com.example.restaurant_simulation.service.CustomerService;
import com.example.restaurant_simulation.service.OrderTicketService;
import com.example.restaurant_simulation.service.handler.CustomerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final CustomerHandler customerHandler;

    @KafkaListener(
            topics = "order-ready",
            autoStartup = "${kafka.consumer.confirmation.enabled:true}",
            concurrency = "3"
    )
    public void consumeConfirmationMessage(String message) {
        customerHandler.orderReady(message);
    }
}