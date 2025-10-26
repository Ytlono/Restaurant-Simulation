package com.example.restaurant_simulation.dto.model;

import com.example.restaurant_simulation.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long customerId;
    private String customerName;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
