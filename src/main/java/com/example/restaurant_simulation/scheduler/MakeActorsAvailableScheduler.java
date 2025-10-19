package com.example.restaurant_simulation.scheduler;

import com.example.restaurant_simulation.service.CookService;
import com.example.restaurant_simulation.service.CustomerService;
import com.example.restaurant_simulation.service.OrderTakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = "restaurant-simulation.scheduler.actors-availability",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class MakeActorsAvailableScheduler {
    private final CustomerService customerService;
    private final CookService cookService;
    private final OrderTakerService orderTakerService;

    @Scheduled(fixedRateString = "${restaurant-simulation.scheduler.actors-availability.customer-rate-ms}")
    public void makeCustomersAvailable() {
        customerService.checkAndUpdateAvailability();
    }

    @Scheduled(fixedRateString = "${restaurant-simulation.scheduler.actors-availability.cook-rate-ms}")
    public void makeCooksAvailable() {
        cookService.checkAndUpdateAvailability();
    }

    @Scheduled(fixedRateString = "${restaurant-simulation.scheduler.actors-availability.order-taker-rate-ms}")
    public void makeOrderTakersAvailable() {
        orderTakerService.checkAndUpdateAvailability();
    }
}
