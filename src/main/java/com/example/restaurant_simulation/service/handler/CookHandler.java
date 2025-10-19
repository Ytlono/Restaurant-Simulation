package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.model.entity.CookEntity;
import com.example.restaurant_simulation.service.CookService;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookHandler extends OrderHandler{
    private final CookService cookService;

    public void handle() {
        CookEntity availableCook = cookService.getAvailable();

        if (availableCook == null) {
            System.out.println("No available cooks found");
            return;
        }

        cookService.processOrder(availableCook);
    }
}
