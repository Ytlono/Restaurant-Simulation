package com.example.restaurant_simulation.service.interfaces;

import com.example.restaurant_simulation.model.entity.SimulatedActorEntity;

public interface ActorServiceInterface<T extends SimulatedActorEntity, S extends Enum<S>> {

    T getAvailable();

    void checkAndUpdateAvailability();

    void updateStatus(Long id, S status);
}
