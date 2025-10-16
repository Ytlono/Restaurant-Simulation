package com.example.restaurant_simulation.service.handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class OrderHandler {
    private OrderHandler next;

    public abstract void handle();

    public void compensate() {
        log.debug("Compensate called for class {}", this.getClass().getSimpleName());
    }

    protected void handleNext() {
    }
}
