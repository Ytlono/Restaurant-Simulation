package com.example.restaurant_simulation.controller;

import com.example.restaurant_simulation.dto.model.CookDto;
import com.example.restaurant_simulation.dto.model.CustomerDto;
import com.example.restaurant_simulation.mapper.CookMapper;
import com.example.restaurant_simulation.model.entity.CookEntity;
import com.example.restaurant_simulation.model.entity.CustomerEntity;
import com.example.restaurant_simulation.service.CookService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cooks")
@RequiredArgsConstructor
public class CookController {
    private final CookService cookService;
    private final CookMapper cookMapper;

    @GetMapping
    public ResponseEntity<List<CookDto>> getAll() {
        return ResponseEntity.ok(
                cookService.getAllCooks()
                        .stream()
                        .map(cookMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CookDto> getById(@PathVariable Long id) {
        CookEntity entity = cookService.getCookById(id);
        return ResponseEntity.ok(
                entity != null ? cookMapper.toDto(entity) : null
        );
    }

    @PostMapping
    public ResponseEntity<CookDto> create(@RequestBody CookDto dto) {
        CookEntity saved = cookService.addCook(cookMapper.toEntity(dto));
        return ResponseEntity.ok(cookMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        cookService.deleteCook(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
