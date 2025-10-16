package com.example.restaurant_simulation.scheduler;

import com.example.restaurant_simulation.service.handler.CustomerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;

@ConditionalOnProperty(
        prefix = "restaurant-simulation.scheduler.create-order",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class CustomerCreateOrderScheduler {
    private final CustomerHandler customerHandler;

    @Scheduled(fixedRateString = "${}")
    public void customerCreateOrder(){
        customerHandler.findCustomerAndMakeOrder();
    }

}
