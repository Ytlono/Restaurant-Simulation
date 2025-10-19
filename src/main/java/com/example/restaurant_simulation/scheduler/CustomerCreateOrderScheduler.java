package com.example.restaurant_simulation.scheduler;

import com.example.restaurant_simulation.service.handler.CookHandler;
import com.example.restaurant_simulation.service.handler.CustomerHandler;
import com.example.restaurant_simulation.service.handler.OrderTakerHandler;
import com.example.restaurant_simulation.service.handler.ServerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = "restaurant-simulation.scheduler.simulation-handler",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class CustomerCreateOrderScheduler {
    private final CustomerHandler customerHandler;
    private final OrderTakerHandler orderTakerHandler;
    private final CookHandler cookHandler;
    private final ServerHandler serverHandler;

    @Scheduled(fixedRateString = "${restaurant-simulation.scheduler.simulation-handler.customer-rate-ms}")
    public void customerHandle() {
        System.out.println("CALLLLLLD");
        customerHandler.handle();
    }

    @Scheduled(fixedRateString = "${restaurant-simulation.scheduler.simulation-handler.order-taker-rate-ms}")
    public void orderTakerHandle() {
        orderTakerHandler.handle();
    }

    @Scheduled(fixedRateString = "${restaurant-simulation.scheduler.simulation-handler.cook-rate-ms}")
    public void cookHandle() {
        cookHandler.handle();
    }

    @Scheduled(fixedRateString = "${restaurant-simulation.scheduler.simulation-handler.server-rate-ms}")
    public void serverHandle() {
        serverHandler.handle();
    }
}
