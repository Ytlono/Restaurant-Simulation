package com.example.restaurant_simulation.model.entity;

import com.example.restaurant_simulation.enums.OrderTicketStatus;
import com.example.restaurant_simulation.enums.TicketType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "kitchen_ticket")
@DiscriminatorValue("KITCHEN")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenTicketEntity extends OrderTicketEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private CookEntity actor;
}
