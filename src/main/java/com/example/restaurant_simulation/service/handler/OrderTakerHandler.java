package com.example.restaurant_simulation.service.handler;

import com.example.restaurant_simulation.model.entity.OrderTakerEntity;
import com.example.restaurant_simulation.service.OrderTakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderTakerHandler extends OrderHandler{
    private final OrderTakerService orderTakerService;

    @Override
    public void handle(){
        OrderTakerEntity orderTaker = orderTakerService.getAvailableCustomer();

        if (orderTaker == null)
            return;

        orderTakerService.processOrderTicket(orderTaker);
    }
}
