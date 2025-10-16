package com.example.restaurant_simulation.consumer;

import com.example.restaurant_simulation.service.CustomerService;
import com.example.restaurant_simulation.service.OrderTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final CustomerService customerService;

    @KafkaListener(
            topics = "order-ready-topic",
            autoStartup = "kafka.consumer.confirmation.enabled:true",
            concurrency = "3"
    )
    public void consumeConfirmationMessage(String message) {
        System.out.println(message);
    }
}