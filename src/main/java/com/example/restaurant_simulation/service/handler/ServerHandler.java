package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.service.OrderService;
import com.example.restaurant_simulation.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;

@Component
@RequiredArgsConstructor
public class ServerHandler extends OrderHandler{
    private final ServerService serverService;

    @Override
    public void handle() {
        serverService.processOrder();
    }
}
