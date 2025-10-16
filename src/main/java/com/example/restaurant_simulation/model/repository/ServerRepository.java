package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.model.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<ServerEntity,Long> {
}
