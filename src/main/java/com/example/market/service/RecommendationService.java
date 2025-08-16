package com.example.market.service;

import com.example.market.dto.RecommendationCreateRequest;
import com.example.market.entity.Recommendation;
import com.example.market.entity.Store;
import com.example.market.repository.RecommendationRepository;
import com.example.market.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final StoreRepository storeRepository;
    private final StoreService storeService;

    @Transactional
    public Recommendation create(RecommendationCreateRequest req) {
        Store store = storeRepository.findById(req.shopId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found: " + req.shopId()));
        // 추천도 인기 카운트에 반영 (가중치 3 예시)
        storeService.increasePopularity(store.getId(), 3);
        return recommendationRepository.save(
                Recommendation.builder().store(store).reason(req.reason()).build()
        );
    }
}