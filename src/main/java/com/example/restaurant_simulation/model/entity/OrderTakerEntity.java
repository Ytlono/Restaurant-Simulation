package com.example.restaurant_simulation.model.entity;

import com.example.restaurant_simulation.enums.CustomerStatus;
import com.example.restaurant_simulation.enums.OrderTakerStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_taker")
@DiscriminatorValue("ORDER_TAKER")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTakerEntity extends SimulatedActorEntity {

    @Column(name = "shift_number")
    private Integer shiftNumber;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTakerStatus status;
}
