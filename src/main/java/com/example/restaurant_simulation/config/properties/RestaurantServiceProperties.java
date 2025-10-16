package com.example.restaurant_simulation.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "restaurant-service")
public class RestaurantServiceProperties {
    private Actors actors;

    @Data
    public static class Actors{
        private Duration cookActorThreshold;
        private Duration orderTakerThreshold;
        private Duration customerThreshold;
    }
}
