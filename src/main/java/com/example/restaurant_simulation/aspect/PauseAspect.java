package com.example.restaurant_simulation.aspect;

import com.example.restaurant_simulation.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PauseAspect {

    private final SimulationService simulationService;

    @Around("@annotation(com.example.restaurant_simulation.aop.Pausable)")
    public Object handlePause(ProceedingJoinPoint joinPoint) throws Throwable {
        simulationService.awaitIfPaused();
        return joinPoint.proceed();
    }
}
