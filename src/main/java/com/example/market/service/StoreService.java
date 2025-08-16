package com.example.market.service;

import com.example.market.dto.StoreDTO;
import com.example.market.entity.Store;
import com.example.market.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<StoreDTO> getStoresByMarket(Long marketId) {
        return storeRepository.findByMarketId(marketId).stream()
                .map(s -> new StoreDTO(s.getId(), s.getName(), s.getCategory(), s.getDescription(), s.getMarket().getId()))
                .collect(Collectors.toList());
    }
}