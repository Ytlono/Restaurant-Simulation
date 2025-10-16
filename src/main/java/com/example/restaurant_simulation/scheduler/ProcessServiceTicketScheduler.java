package com.example.restaurant_simulation.scheduler;

import com.example.restaurant_simulation.service.handler.OrderTakerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;

@ConditionalOnProperty(
        prefix = "restaurant-simulation.scheduler.process-service-ticket",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class ProcessServiceTicketScheduler {
    private final OrderTakerHandler orderTakerHandler;

    @Scheduled(fixedRateString = "${}")
    public void processServiceTicket(){
        orderTakerHandler.handle();
    }
}
