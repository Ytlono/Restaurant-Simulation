package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.producer.MessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {

    private final ObjectMapper objectMapper;
    private final MessageProducer messageProducer;

    public void publish(Object event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            messageProducer.send("", message);
            log.info("Event published successfully: {}", event.getClass().getSimpleName());
        } catch (JsonProcessingException e) {
            log.error("Error serializing event: {}", event, e);
        } catch (Exception e) {
            log.error("Error publishing event: {}", event, e);
        }
    }
}

