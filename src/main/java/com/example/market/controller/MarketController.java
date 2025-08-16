package com.example.market.controller;

import com.example.market.entity.Market;
import com.example.market.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markets")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;

    // 시장 전체 조회
    @GetMapping
    public List<Market> findAll() { return marketService.findAll(); }

    // 시장 단일 조회
    @GetMapping("/{id}")
    public Market findOne(@PathVariable Long id) { return marketService.findById(id); }
}