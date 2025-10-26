package com.example.restaurant_simulation.controller;

import com.example.restaurant_simulation.dto.model.CustomerDto;
import com.example.restaurant_simulation.dto.model.OrderTakerDto;
import com.example.restaurant_simulation.mapper.OrderTakerMapper;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.model.entity.OrderTakerEntity;
import com.example.restaurant_simulation.service.OrderTakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-tackers")
@RequiredArgsConstructor
public class OrderTakerController {
    private final OrderTakerService orderTakerService;
    private final OrderTakerMapper orderTakerMapper;

    @GetMapping
    public ResponseEntity<List<OrderTakerDto>> getAll() {
        return ResponseEntity.ok(
                orderTakerService.getAllOrderTakers()
                        .stream()
                        .map(orderTakerMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTakerDto> getById(@PathVariable Long id) {
        OrderTakerEntity entity = orderTakerService.getOrderTakerById(id);
        return ResponseEntity.ok(
                entity != null ? orderTakerMapper.toDto(entity) : null
        );
    }

    @PostMapping
    public ResponseEntity<OrderTakerDto> create(@RequestBody OrderTakerDto dto) {
        OrderTakerEntity saved = orderTakerService.addOrderTaker(
                orderTakerMapper.toEntity(dto)
        );
        return ResponseEntity.ok(orderTakerMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderTakerService.deleteOrderTaker(id);
    }
}
