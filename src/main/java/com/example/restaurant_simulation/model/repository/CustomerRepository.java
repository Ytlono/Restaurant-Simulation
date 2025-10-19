package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    @Query("SELECT c FROM CustomerEntity c WHERE c.ticket.id = :ticketId")
    Optional<CustomerEntity> findCustomerByTicketId(@Param("ticketId") Long ticketId);

    @Query("SELECT c FROM CustomerEntity c WHERE c.status = :status ORDER BY c.updatedAt ASC LIMIT 1")
    CustomerEntity findOldestCustomerByStatus(@Param("status") CustomerStatus status);

    List<CustomerEntity> findByStatusAndUpdatedAtBefore(CustomerStatus status, Instant threshold);
}