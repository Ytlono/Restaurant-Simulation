package com.example.restaurant_simulation.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(
        prefix = "restaurant-simulation.scheduler.enable-customer",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class MakeCustomerAvailableScheduler {
}
