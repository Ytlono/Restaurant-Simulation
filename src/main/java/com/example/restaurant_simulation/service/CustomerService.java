package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.repository.CustomerRepository;
import com.example.restaurant_simulation.service.interfaces.ActorServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CustomerService implements ActorServiceInterface<CustomerEntity,CustomerStatus> {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final RestaurantServiceProperties properties;

    @Transactional
    public void makeOrder(CustomerEntity customerEntity){
        updateStatus(customerEntity.getId(),CustomerStatus.WAITING_FOR_ORDER);
        orderService.createOrder(customerEntity);
    }

    @Override
    public CustomerEntity getAvailable(){
        return customerRepository.findOldestCustomerByStatus(
                CustomerStatus.AVAILABLE
        );
    }

    @Override
    @Transactional
    public void checkAndUpdateAvailability(CustomerEntity customer) {
        Instant threshold = Instant.now().minus(
                properties.getActors().getCookActorThreshold()
        );

        if (!threshold.isBefore(customer.getUpdatedAt())) return;

        updateStatus(customer.getId(),CustomerStatus.AVAILABLE);
    }

    @Override
    public void updateStatus(Long id, CustomerStatus status){
        customerRepository.updateStatus(id,status);
    }
}
