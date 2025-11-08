package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.model.entity.CookEntity;
import com.example.restaurant_simulation.service.CookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookHandler extends RestaurantHandler {
    private final CookService cookService;

    public void handle() {
        CookEntity availableCook = cookService.getAvailable();

        if (availableCook == null) {
            return;
        }

        cookService.processOrder(availableCook);
    }
}
