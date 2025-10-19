package com.example.restaurant_simulation.service;

import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderTicketEntity;
import com.example.restaurant_simulation.model.entity.ServiceTicketEntity;
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
            System.out.println("No available customers found");
            return;
        }
        updateStatus(customerEntity.getId(),CustomerStatus.WAITING_FOR_ORDER);
        orderService.createOrder(customerEntity);
    }

    @Transactional
    public void takeOrder(Long orderId){
        System.out.println("Looking for service ticket with order id: " + orderId);

        ServiceTicketEntity serviceTicket = orderTicketService.findServiceTicketByOrderId(orderId);

        if (serviceTicket == null) {
            System.out.println("Service ticket not found for order id: " + orderId);
            return;
        }

        System.out.println("Found service ticket: " + serviceTicket.getId() + " for order: " + orderId);

        Optional<CustomerEntity> customerOpt = customerRepository.findCustomerByTicketId(serviceTicket.getId());

        if (customerOpt.isEmpty()) {
            System.out.println("Customer not found for ticket id: " + serviceTicket.getId());
            return;
        }

        System.out.println("Customer FOUND for ticket id: " + serviceTicket.getId());
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
        // Entity подход вместо @Modifying запроса
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        customer.setStatus(status);
        customerRepository.save(customer); // ← Теперь updatedAt обновится!
    }

    @Override
    @Transactional
    public void checkAndUpdateAvailability() {
        Instant threshold = Instant.now().minus(
                properties.getActors().getCustomerThreshold()
        );

        // Оптимизированный подход с использованием репозитория
        List<CustomerEntity> expiredCustomers = customerRepository.findByStatusAndUpdatedAtBefore(
                CustomerStatus.EATING, threshold
        );

        // Если метод выше не работает, используйте этот вариант:
        // List<CustomerEntity> expiredCustomers = customerRepository.findAll()
        //         .stream()
        //         .filter(customer ->
        //                 customer.getStatus() == CustomerStatus.EATING &&
        //                         customer.getUpdatedAt().isBefore(threshold)
        //         )
        //         .toList();

        expiredCustomers.forEach(customer -> {
            customer.setStatus(CustomerStatus.AVAILABLE);
            customerRepository.save(customer); // ← Обновит updatedAt!
        });

        System.out.println("CALLLD CUSTOMER AVAIBILITY: " + expiredCustomers.size());
        log.info("Updated {} expired customers from EATING to AVAILABLE", expiredCustomers.size());
    }

    @Transactional
    public void setCustomerServiceTicket(Long ticketId){
        ServiceTicketEntity ticket = orderTicketService.findById(ticketId);

        CustomerEntity customer = ticket.getOrder().getCustomerEntity();
        customer.setTicket(ticket);

        customerRepository.save(customer); // ← Этот save обновит updatedAt
        updateStatus(customer.getId(), CustomerStatus.WAITING_FOR_FOOD); // ← И этот тоже
    }
}