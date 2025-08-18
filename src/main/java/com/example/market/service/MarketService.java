package com.example.market.service;

import com.example.market.entity.Market;
import com.example.market.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    public List<Market> findAll() {
        return marketRepository.findAll();
    }

    public Market findById(Long id) {
        return marketRepository.findByIdWithStores(id)
                .orElseThrow(() -> new IllegalArgumentException("Market not found: " + id));
    }
}