package com.example.market.service;

import com.example.market.domain.*;
import com.example.market.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {
    private final ShopRepository shopRepository;
    private final ShopSearchLogRepository searchLogRepository;
    private final RecommendationLogRepository recLogRepository;
    private final PopularityService popularityService;

    @Transactional
    public void logSearch(Long shopId, String keyword, String sessionId){
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        searchLogRepository.save(
                ShopSearchLog.builder().shop(shop).keyword(keyword).userSessionId(sessionId).build()
        );
        popularityService.incr(shop.getMarket().getMarketId(), shopId, 1.0);
    }

    @Transactional
    public void logRecommend(Long shopId, String reason, String sessionId){
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        recLogRepository.save(
                RecommendationLog.builder().shop(shop).reason(reason).userSessionId(sessionId).build()
        );
        popularityService.incr(shop.getMarket().getMarketId(), shopId, 1.0);
    }
}