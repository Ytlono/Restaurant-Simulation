package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.model.entity.SimulatedActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulatedActorRepository extends JpaRepository<SimulatedActorEntity,Long> {
}
