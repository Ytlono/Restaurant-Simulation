package com.example.restaurant_simulation.dto.event;

import lombok.Builder;


public record OrderReadyEvent(
    Long orderId
)
{
    @Builder
    public OrderReadyEvent {};
}
