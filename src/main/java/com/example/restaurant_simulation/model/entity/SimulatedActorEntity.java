package com.example.restaurant_simulation.model.entity;

import com.example.restaurant_simulation.enums.ActorRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "simulated_actor")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "actor_role",discriminatorType = DiscriminatorType.STRING)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SimulatedActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ActorRole role;

    @CreationTimestamp(source = SourceType.VM) // ← ИЗМЕНИТЕ НА VM
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp(source = SourceType.VM) // ← ИЗМЕНИТЕ НА VM
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}