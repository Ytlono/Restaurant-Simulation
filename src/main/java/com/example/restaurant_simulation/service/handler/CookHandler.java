package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.service.CookService;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookHandler extends OrderHandler{
    private final CookService cookService;

    @Override
    public void handle() {
        cookService.processOrder(
                cookService.getAvailable()
        );
    }
}
