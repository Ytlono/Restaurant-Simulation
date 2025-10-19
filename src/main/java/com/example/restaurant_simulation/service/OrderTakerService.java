package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import com.example.restaurant_simulation.enums.*;
import com.example.restaurant_simulation.model.entity.*;
import com.example.restaurant_simulation.model.repository.OrderTakerRepository;
import com.example.restaurant_simulation.service.handler.OrderTakerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderTakerService {
    private final RestaurantServiceProperties properties;
    private final OrderTakerRepository orderTakerRepository;
    private final OrderTicketService ticketService;
    private final CustomerService customerService;
    private final OrderService orderService;

    @Transactional
    public void processOrderTicket(OrderTakerEntity orderTaker){
        OrderEntity order = orderService.getLatestPendingOrder();

        if (order == null) {
            System.out.println("No pending orders found");
            return;
        }

        // Обновляем статус через entity подход
        updateOrderTakerStatus(orderTaker.getId(), OrderTakerStatus.PROCESSING);
        orderService.updateStatus(order.getId(), OrderStatus.IN_PROGRESS);

        ServiceTicketEntity serviceTicket = (ServiceTicketEntity) ticketService.createOrderTicketByType(
                order,
                TicketType.SERVICE
        );

        ticketService.createOrderTicketByType(
                order,
                TicketType.KITCHEN
        );

        customerService.setCustomerServiceTicket(serviceTicket.getId());
    }

    public OrderTakerEntity getAvailableCustomer(){
        return orderTakerRepository.findFirstByStatusOrderByUpdatedAtAsc(
                OrderTakerStatus.AVAILABLE
        );
    }

    // ИСПРАВЛЕННЫЙ МЕТОД - убрал REQUIRES_NEW и используем entity подход
    @Transactional
    public void updateOrderTakerStatus(Long id, OrderTakerStatus status){
        OrderTakerEntity orderTaker = orderTakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderTaker not found: " + id));
        orderTaker.setStatus(status);
        orderTakerRepository.save(orderTaker); // ← Теперь updatedAt обновится!
    }

    @Transactional
    public void checkAndUpdateAvailability() {
        Instant threshold = Instant.now().minus(
                properties.getActors().getOrderTakerThreshold()
        );

        List<OrderTakerEntity> expiredOrderTakers = orderTakerRepository.findAll()
                .stream()
                .filter(ot -> ot.getUpdatedAt().isBefore(threshold))
                .toList();

        expiredOrderTakers.forEach(orderTaker -> {
            orderTaker.setStatus(OrderTakerStatus.AVAILABLE);
            orderTakerRepository.save(orderTaker); // ← Обновит updatedAt!
        });

        System.out.println("Updated " + expiredOrderTakers.size() + " expired order takers to AVAILABLE");
    }
}
