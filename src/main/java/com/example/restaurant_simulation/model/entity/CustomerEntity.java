package com.example.restaurant_simulation.model.entity;

import com.example.restaurant_simulation.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "customers")
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity extends SimulatedActorEntity {
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private OrderTicketEntity ticket;
}
