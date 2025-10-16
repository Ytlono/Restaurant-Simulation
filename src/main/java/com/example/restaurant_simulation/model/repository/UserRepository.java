package com.example.restaurant_simulation.model.repository;

import com.example.restaurant_simulation.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
