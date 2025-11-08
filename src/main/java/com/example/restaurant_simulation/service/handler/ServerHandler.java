package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerHandler extends RestaurantHandler {
    private final ServerService serverService;

    @Override
    public void handle() {
        serverService.processOrder();
    }
}
