package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    @Query(value = """
    SELECT * FROM customer 
    WHERE status = :status 
    ORDER BY updated_at ASC 
    LIMIT 1
    """, nativeQuery = true)
    CustomerEntity findOldestCustomerByStatus(@Param("status") CustomerStatus status);

    @Modifying
    @Query("UPDATE CustomerEntity n SET n.status = :status WHERE n.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") CustomerStatus status);

}
