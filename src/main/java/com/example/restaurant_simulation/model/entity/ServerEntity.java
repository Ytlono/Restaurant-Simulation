package com.example.restaurant_simulation.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "servers")
@DiscriminatorValue("SERVER")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ServerEntity extends SimulatedActorEntity {

    @Column(name = "experience_years")
    private Integer experienceYears;
}
