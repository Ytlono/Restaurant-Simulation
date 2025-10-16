package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerHandler extends OrderHandler{
    private final CustomerService customerService;

    public void handle(){

    }
}
