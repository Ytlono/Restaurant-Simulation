package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.aspect.Pausable;
import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import com.example.restaurant_simulation.enums.*;
import com.example.restaurant_simulation.model.entity.*;
import com.example.restaurant_simulation.model.repository.OrderTakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Pausable
@Service
@RequiredArgsConstructor
public class OrderTakerService {
    private final RestaurantServiceProperties properties;
    private final OrderTakerRepository orderTakerRepository;
    private final OrderTicketService ticketService;
    private final CustomerService customerService;
    private final OrderService orderService;

    public List<OrderTakerEntity> getAllOrderTakers() {
        return orderTakerRepository.findAll();
    }

    public OrderTakerEntity getOrderTakerById(Long id) {
        return orderTakerRepository.findById(id).orElseThrow();
    }

    public OrderTakerEntity addOrderTaker(OrderTakerEntity entity) {
        return orderTakerRepository.save(entity);
    }

    public void deleteOrderTaker(Long id) {
        orderTakerRepository.deleteById(id);
    }

    @Transactional
    public void processOrderTicket(OrderTakerEntity orderTaker){
        OrderEntity order = orderService.getLatestPendingOrder();

        if (order == null) {
            return;
        }

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

    @Transactional
    public void updateOrderTakerStatus(Long id, OrderTakerStatus status){
        OrderTakerEntity orderTaker = orderTakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderTaker not found: " + id));
        orderTaker.setStatus(status);
        orderTakerRepository.save(orderTaker);
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

    }

}
