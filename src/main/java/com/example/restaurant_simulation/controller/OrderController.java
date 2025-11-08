package com.example.restaurant_simulation.controller;

import com.example.restaurant_simulation.dto.model.OrderDto;
import com.example.restaurant_simulation.dto.request.OrderRequest;
import com.example.restaurant_simulation.enums.OrderSorting;
import com.example.restaurant_simulation.mapper.OrderMapper;
import com.example.restaurant_simulation.mapper.OrderSortMapper;
import com.example.restaurant_simulation.model.entity.OrderEntity;
import com.example.restaurant_simulation.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderSortMapper orderSortMapper;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<Page<OrderDto>> getAll(
            @RequestBody OrderRequest orderRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "CREATED_AT_DESC") OrderSorting orderSorting
    ) {
        Sort sort = orderSortMapper.toSort(orderSorting);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OrderEntity> orderPage = orderService.getAllBySpecs(orderRequest, pageable);

        Page<OrderDto> orderDtoPage = orderPage.map(orderMapper::toDto);

        if (!orderDtoPage.getContent().isEmpty()) {
            OrderDto firstDto = orderDtoPage.getContent().get(0);
            System.out.println("=== ПОСЛЕ МАППИНГА В DTO ===");
            System.out.println("DTO ID: " + firstDto.getId());
            System.out.println("DTO CustomerId: " + firstDto.getCustomerId());
            System.out.println("DTO CustomerName: " + firstDto.getCustomerName());
            System.out.println("DTO Status: " + firstDto.getStatus());
        }

        return ResponseEntity.ok(orderDtoPage);
    }
}