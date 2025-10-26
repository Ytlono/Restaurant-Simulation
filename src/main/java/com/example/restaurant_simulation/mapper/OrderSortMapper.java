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
            case CREATED_AT_DESC -> Sort.by("createdAt").descending();
            case CREATED_AT_ASC -> Sort.by("createdAt").ascending();
            case UPDATED_AT_DESC -> Sort.by("updatedAt").descending();
            case UPDATED_AT_ASC -> Sort.by("updatedAt").ascending();
            case CUSTOMER_ID_DESC -> Sort.by("customer.id").descending();
            case CUSTOMER_ID_ASC -> Sort.by("customer.id").ascending();
        };
    }
}
