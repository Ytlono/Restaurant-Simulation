package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Getter
@Service
@RequiredArgsConstructor
public class SimulationService {

    private final RestaurantServiceProperties properties;

    private volatile boolean paused = false;
    private Instant pauseStart;
    private Duration totalPaused = Duration.ZERO;

    public synchronized void pause() {
        if (!paused) {
            paused = true;
            pauseStart = Instant.now();
        }
    }

    public synchronized void resume() {
        if (paused) {
            Duration thisPause = Duration.between(pauseStart, Instant.now());
            totalPaused = totalPaused.plus(thisPause);
            paused = false;
        }
    }

    public void awaitIfPaused() {
        while (paused) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public void updateCookThreshold(Duration duration) {
        properties.getActors().setCookActorThreshold(duration);
    }

    public void updateOrderTakerThreshold(Duration duration) {
        properties.getActors().setOrderTakerThreshold(duration);
    }

    public void updateCustomerThreshold(Duration duration) {
        properties.getActors().setCustomerThreshold(duration);
    }
}
