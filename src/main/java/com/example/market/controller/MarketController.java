package com.example.market.controller;

import com.example.market.dto.MarketDTO;
import com.example.market.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/markets")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;

    @GetMapping
    public List<MarketDTO> getAllMarkets() {
        return marketService.getAllMarkets();
    }
}