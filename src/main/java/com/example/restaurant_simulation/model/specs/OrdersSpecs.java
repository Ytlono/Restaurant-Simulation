package com.example.restaurant_simulation.model.specs;

import com.example.restaurant_simulation.enums.OrderStatus;
import com.example.restaurant_simulation.model.entity.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;

public class OrdersSpecs {

    public static Specification<OrderEntity> customerIn(List<Long> customerIds) {
        return (root, query, cb) -> root.get("customerEntity").get("id").in(customerIds);
    }

    public static Specification<OrderEntity> statusIn(List<OrderStatus> statuses) {
        return (root, query, cb) -> root.get("status").in(statuses);
    }

    public static Specification<OrderEntity> createdBetween(Instant from, Instant to) {
        return (root, query, cb) -> cb.between(root.get("createdAt"), from, to);
    }

    public static Specification<OrderEntity> updatedBetween(Instant from, Instant to) {
        return (root, query, cb) -> cb.between(root.get("updatedAt"), from, to);
    }

    public static Specification<OrderEntity> createdAfter(Instant from) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), from);
    }

    public static Specification<OrderEntity> createdBefore(Instant to) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), to);
    }

    public static Specification<OrderEntity> updatedAfter(Instant from) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("updatedAt"), from);
    }

    public static Specification<OrderEntity> updatedBefore(Instant to) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("updatedAt"), to);
    }

    public static Specification<OrderEntity> byStatus(OrderStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
