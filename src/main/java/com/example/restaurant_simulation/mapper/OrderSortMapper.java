package com.example.restaurant_simulation.mapper;

import com.example.restaurant_simulation.enums.OrderSorting;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class OrderSortMapper {

    public Sort toSort(OrderSorting sort) {
        if (sort == null) {
            return Sort.unsorted();
        }

        return switch (sort) {
            case CREATED_AT_ASC -> Sort.by(Sort.Direction.ASC, "createdAt");
            case CREATED_AT_DESC -> Sort.by(Sort.Direction.DESC, "createdAt");
            case UPDATED_AT_ASC -> Sort.by(Sort.Direction.ASC, "updatedAt");
            case UPDATED_AT_DESC -> Sort.by(Sort.Direction.DESC, "updatedAt");
            case CUSTOMER_ID_ASC -> Sort.by(Sort.Direction.ASC, "customerEntity.id");
            case CUSTOMER_ID_DESC -> Sort.by(Sort.Direction.DESC, "customerEntity.id");
        };
    }
}
