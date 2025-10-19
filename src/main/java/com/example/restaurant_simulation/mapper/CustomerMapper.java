package com.example.restaurant_simulation.mapper;

import com.example.restaurant_simulation.dto.model.CustomerDto;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerDto toDto(CustomerEntity entity);
    CustomerEntity toEntity(CustomerDto dto);
}
