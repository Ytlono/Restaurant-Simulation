package com.example.restaurant_simulation.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_takers")
@DiscriminatorValue("ORDER_TAKER")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTakerEntity extends SimulatedActorEntity {

    @Column(name = "shift_number")
    private Integer shiftNumber;
}
