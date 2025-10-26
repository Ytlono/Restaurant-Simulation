package com.example.restaurant_simulation.model.entity;

import com.example.restaurant_simulation.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "customer")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private ServiceTicketEntity ticket;
}
