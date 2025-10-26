package com.example.restaurant_simulation.dto.model;

import com.example.restaurant_simulation.enums.CookStatus;
import com.example.restaurant_simulation.enums.OrderTakerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CookDto {
    private Long id;
    private String name;
    private Long orderId;
    private Long kitchenTicketId;
    private CookStatus status;
}
