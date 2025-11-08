package com.example.restaurant_simulation.controller;

import com.example.restaurant_simulation.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/simulation")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulationService simulationService;

    @PostMapping("/pause")
    public ResponseEntity<String> pauseSimulation() {
        simulationService.pause();
        return ResponseEntity.ok("Симуляция поставлена на паузу");
    }

    @PostMapping("/resume")
    public ResponseEntity<String> resumeSimulation() {
        simulationService.resume();
        return ResponseEntity.ok("Симуляция возобновлена");
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        boolean paused = simulationService.isPaused();
        long totalPausedSeconds = simulationService.getTotalPaused().getSeconds();
        return ResponseEntity.ok("Paused: " + paused + ", totalPaused: " + totalPausedSeconds + " сек.");
    }

    @PostMapping("/threshold/cook")
    public ResponseEntity<String> updateCookThreshold(@RequestParam long seconds) {
        simulationService.updateCookThreshold(Duration.ofSeconds(seconds));
        return ResponseEntity.ok("Порог повара изменён на " + seconds + " сек.");
    }

    @PostMapping("/threshold/order-taker")
    public ResponseEntity<String> updateOrderTakerThreshold(@RequestParam long seconds) {
        simulationService.updateOrderTakerThreshold(Duration.ofSeconds(seconds));
        return ResponseEntity.ok("Порог принимающего заказы изменён на " + seconds + " сек.");
    }

    @PostMapping("/threshold/customer")
    public ResponseEntity<String> updateCustomerThreshold(@RequestParam long seconds) {
        simulationService.updateCustomerThreshold(Duration.ofSeconds(seconds));
        return ResponseEntity.ok("Порог клиента изменён на " + seconds + " сек.");
    }
}
