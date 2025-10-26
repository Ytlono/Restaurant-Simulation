package com.example.restaurant_simulation.mapper;

import com.example.restaurant_simulation.dto.model.CookDto;
import com.example.restaurant_simulation.dto.model.CustomerDto;
import com.example.restaurant_simulation.model.entity.CookEntity;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CookMapper {

    @Mapping(target = "orderId", source = "ticket.order.id")
    @Mapping(target = "kitchenTicketId", source = "ticket.id")
    CookDto toDto(CookEntity entity);

    CookEntity toEntity(CookDto dto);
}
