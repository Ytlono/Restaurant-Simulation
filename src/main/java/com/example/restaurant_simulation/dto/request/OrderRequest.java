package com.example.restaurant_simulation.dto.request;

import com.example.restaurant_simulation.enums.OrderSorting;
import com.example.restaurant_simulation.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<Long> userIds;

    private List<OrderStatus> statuses;

    private Instant minDate;
    private Instant maxDate;
}
