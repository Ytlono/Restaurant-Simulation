package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.dto.event.OrderReadyEvent;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerHandler extends OrderHandler{
    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    public void handle(){
        CustomerEntity customer = customerService.getAvailable();
        customerService.makeOrder(customer);
    }

    public void orderReady(String order){
        try {
            OrderReadyEvent event = objectMapper.readValue(order,OrderReadyEvent.class);
            System.out.println("MAPPPPED EVENT" + event.orderId());
            customerService.takeOrder(event.orderId());
        }catch (JsonProcessingException e){
            log.error("Order ready processing exception");
        }
    }
}
