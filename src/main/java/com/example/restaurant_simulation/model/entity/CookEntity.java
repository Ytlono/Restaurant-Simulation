package com.example.restaurant_simulation.model.entity;

import com.example.restaurant_simulation.enums.CookStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cook")
@DiscriminatorValue("COOK")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CookEntity extends SimulatedActorEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CookStatus status;

    @Column(name = "speciality")
    private String speciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private KitchenTicketEntity ticket;
}
