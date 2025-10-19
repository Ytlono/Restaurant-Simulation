package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.enums.OrderStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderEntity;
import com.example.restaurant_simulation.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private  final OrderNumGenerator orderNumGenerator;
    private final OrderRepository orderRepository;

    public void createOrder(CustomerEntity customerEntity){
        OrderEntity orderEntity = OrderEntity.builder()
                .orderNumber(orderNumGenerator.generateQueuePos())
                .customerEntity(customerEntity)
                .status(OrderStatus.PENDING)
                .build();

        orderRepository.save(orderEntity);
    }

    public OrderEntity getLatestPendingOrder(){
        return orderRepository.findFirstByStatusOrderByUpdatedAtAsc(OrderStatus.PENDING);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(Long id, OrderStatus status){
        orderRepository.updateStatus(id,status);
    }
}
