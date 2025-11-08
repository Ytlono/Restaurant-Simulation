package com.example.restaurant_simulation.mapper;

import com.example.restaurant_simulation.dto.model.OrderDto;
import com.example.restaurant_simulation.model.entity.OrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "customerId", source = "customerEntity.id")
    @Mapping(target = "customerName", source = "customerEntity.name")
    OrderDto toDto(OrderEntity order);
}
