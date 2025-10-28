package com.example.restaurant_simulation.controller;

import com.example.restaurant_simulation.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(
                "Paused: " + paused + ", totalPaused: " + totalPausedSeconds + " сек."
        );
    }
}
