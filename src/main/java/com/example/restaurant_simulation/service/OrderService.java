package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.aspect.Pausable;
import com.example.restaurant_simulation.dto.request.OrderRequest;
import com.example.restaurant_simulation.enums.OrderStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderEntity;
import com.example.restaurant_simulation.model.repository.OrderRepository;
import com.example.restaurant_simulation.model.specs.OrdersSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Pausable
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<OrderEntity> getAllBySpecs(OrderRequest request, Pageable pageable) {
        List<Specification<OrderEntity>> specs = new ArrayList<>();

        if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
            specs.add(OrdersSpecs.customerIn(request.getUserIds()));
        }

        if (request.getMinDate() != null && request.getMaxDate() != null) {
            specs.add(OrdersSpecs.createdBetween(request.getMinDate(), request.getMaxDate()));
        }

        if (request.getStatuses() != null && !request.getStatuses().isEmpty()) {
            specs.add(OrdersSpecs.statusIn(request.getStatuses()));
        }

        Specification<OrderEntity> spec = specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());

        return orderRepository.findAll(spec, pageable);
    }


    public void createOrder(CustomerEntity customerEntity){
        OrderEntity orderEntity = OrderEntity.builder()
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
