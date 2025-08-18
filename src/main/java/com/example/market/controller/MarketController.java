package com.example.market.controller;

import com.example.market.dto.MarketDetailResponse;
import com.example.market.dto.MarketResponse;
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

    // 시장 전체 조회 (stores 제외)
    @GetMapping
    public List<MarketResponse> findAll() {
        return marketService.findAll().stream()
                .map(MarketResponse::from)
                .toList();
    }

    // 시장 단일 조회 (stores 포함)
    @GetMapping("/{id}")
    public MarketDetailResponse findOne(@PathVariable Long id) {
        Market market = marketService.findById(id);
        return MarketDetailResponse.from(market);
    }
}