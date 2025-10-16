package com.example.restaurant_simulation.config;

import com.example.restaurant_simulation.config.properties.RestaurantServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RestaurantServiceProperties.class)
public class RestaurantServiceConfig {
}
