package com.example.market.controller;

import com.example.market.dto.StoreDTO;
import com.example.market.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/market/{marketId}")
    public List<StoreDTO> getStoresByMarket(@PathVariable Long marketId) {
        return storeService.getStoresByMarket(marketId);
    }
}