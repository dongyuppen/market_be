package com.example.market.service;

import com.example.market.dto.MarketDTO;
import com.example.market.entity.Market;
import com.example.market.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    public List<MarketDTO> getAllMarkets() {
        return marketRepository.findAll().stream()
                .map(m -> new MarketDTO(m.getId(), m.getName(), m.getLocation(), m.getDescription()))
                .collect(Collectors.toList());
    }
}