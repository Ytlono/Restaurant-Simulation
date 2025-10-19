package com.example.restaurant_simulation.producer;

import org.springframework.kafka.support.SendResult;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CompletableFuture<SendResult<String, String>> send(String topic, String message) {
        System.out.println("MESSSSSSSSAGEEEEEEEE PERODUCERRRRRR CALLLLLLLLLLLLLLLLED");
        return kafkaTemplate.send(topic, message);
    }
}
