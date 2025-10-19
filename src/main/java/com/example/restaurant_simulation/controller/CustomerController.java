package com.example.restaurant_simulation.controller;

import com.example.restaurant_simulation.dto.model.CustomerDto;
import com.example.restaurant_simulation.mapper.CustomerMapper;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(
                customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        CustomerEntity entity = customerService.getCustomerById(id);
        return ResponseEntity.ok(
                 entity != null ? customerMapper.toDto(entity) : null
            );
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto dto) {
        CustomerEntity saved = customerService.addCustomer(customerMapper.toEntity(dto));
        return ResponseEntity.ok(customerMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
