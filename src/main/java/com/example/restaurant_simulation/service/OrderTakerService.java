package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.enums.*;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderEntity;
import com.example.restaurant_simulation.model.entity.OrderTakerEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.repository.OrderTakerRepository;
import com.example.restaurant_simulation.service.handler.OrderTakerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderTakerService {
    private final OrderTakerRepository orderTakerRepository;
    private final OrderTicketService ticketService;
    private final  OrderService orderService;

    @Transactional
    public void processOrderTicket(OrderTakerEntity orderTaker){
        updateOrderTakerStatus(orderTaker.getId(),OrderTakerStatus.PROCESSING);

        OrderEntity order = orderService.getLatestPendingOrder();
        orderService.updateStatus(order.getId(), OrderStatus.IN_PROGRESS);

        ticketService.createOrderTicketByType(
                order,
                TicketType.SERVICE
        );

        ticketService.createOrderTicketByType(
                order,
                TicketType.KITCHEN
        );
    }

    public OrderTakerEntity getAvailableCustomer(){
        return orderTakerRepository.findFirstByStatusOrderByUpdatedAtAsc(
                OrderTakerStatus.AVAILABLE
        );
    }

    public void updateOrderTakerStatus(Long id,OrderTakerStatus status){
        orderTakerRepository.updateStatus(id,status);
    }
}
