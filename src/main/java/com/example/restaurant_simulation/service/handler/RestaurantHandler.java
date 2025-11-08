package com.example.restaurant_simulation.service.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class RestaurantHandler {
    private RestaurantHandler next;

    public abstract void handle();
}
