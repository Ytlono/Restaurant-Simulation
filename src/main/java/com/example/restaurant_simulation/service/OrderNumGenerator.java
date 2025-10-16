package com.example.restaurant_simulation.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderNumGenerator {
    public Integer generateQueuePos(){
        return ThreadLocalRandom.current().nextInt(
                100,1000
        );
    }
}
