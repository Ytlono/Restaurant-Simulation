package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.aspect.Pausable;
import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import com.example.restaurant_simulation.enums.ActorRole;
import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.entity.ServiceTicketEntity;
import com.example.restaurant_simulation.model.entity.SimulatedActorEntity;
import com.example.restaurant_simulation.model.repository.CustomerRepository;
import com.example.restaurant_simulation.model.repository.OrderTicketRepository;
import com.example.restaurant_simulation.service.interfaces.ActorServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Pausable
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements ActorServiceInterface<CustomerEntity,CustomerStatus> {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final OrderTicketService orderTicketService;
    private final RestaurantServiceProperties properties;

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public CustomerEntity addCustomer(CustomerEntity customer) {
        customer.setRole(ActorRole.CUSTOMER);
        customer.setStatus(CustomerStatus.AVAILABLE);
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    public void makeOrder(CustomerEntity customerEntity){
        if (customerEntity == null) {
            return;
        }
        updateStatus(customerEntity.getId(),CustomerStatus.WAITING_FOR_ORDER);
        orderService.createOrder(customerEntity);
    }

    @Transactional
    public void takeOrder(Long orderId){

        ServiceTicketEntity serviceTicket = orderTicketService.findServiceTicketByOrderId(orderId);

        if (serviceTicket == null) {
            return;
        }


        Optional<CustomerEntity> customerOpt = customerRepository.findCustomerByTicketId(serviceTicket.getId());

        if (customerOpt.isEmpty()) {
            return;
        }

        CustomerEntity customer = customerOpt.get();
        updateStatus(customer.getId(), CustomerStatus.EATING);
    }

    @Override
    public CustomerEntity getAvailable(){
        return customerRepository.findOldestCustomerByStatus(
                CustomerStatus.AVAILABLE
        );
    }

    @Override
    public void updateStatus(Long id, CustomerStatus status){
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        customer.setStatus(status);
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void checkAndUpdateAvailability() {
        Instant threshold = Instant.now().minus(
                properties.getActors().getCustomerThreshold()
        );

        List<CustomerEntity> expiredCustomers = customerRepository.findByStatusAndUpdatedAtBefore(
                CustomerStatus.EATING, threshold
        );

        expiredCustomers.forEach(customer -> {
            customer.setStatus(CustomerStatus.AVAILABLE);
            customerRepository.save(customer);
        });

        log.info("Updated {} expired customers from EATING to AVAILABLE", expiredCustomers.size());
    }

    @Transactional
    public void setCustomerServiceTicket(Long ticketId){
        ServiceTicketEntity ticket = orderTicketService.findById(ticketId);

        CustomerEntity customer = ticket.getOrder().getCustomerEntity();
        customer.setTicket(ticket);

        customerRepository.save(customer);
        updateStatus(customer.getId(), CustomerStatus.WAITING_FOR_FOOD);
    }
}