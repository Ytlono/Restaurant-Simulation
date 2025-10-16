package com.example.restaurant_simulation.dto.event;

import lombok.Builder;
import lombok.Data;


public record OrderReadyEvent(
    Long orderId
)
{
    @Builder
    public OrderReadyEvent {};
}
