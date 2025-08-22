package com.example.market.service;

import com.example.market.dto.PopularStoreResponse;
import com.example.market.dto.StoreCreateRequest;
import com.example.market.dto.StoreUpdateRequest;
import com.example.market.entity.Market;
import com.example.market.entity.Store;
import com.example.market.entity.StorePopularity;
import com.example.market.repository.MarketRepository;
import com.example.market.repository.StorePopularityRepository;
import com.example.market.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MarketRepository marketRepository;
    private final StorePopularityRepository popularityRepository;

    public List<Store> findByMarket(Long marketId) {
        return storeRepository.findByMarket_Id(marketId);
    }

    public Store findById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Store not found: " + id));
    }

    @Transactional
    public Store create(StoreCreateRequest req) {
        Market market = marketRepository.findById(req.marketId())
                .orElseThrow(() -> new IllegalArgumentException("Market not found: " + req.marketId()));
        Store saved = storeRepository.save(
                Store.builder()
                        .market(market)
                        .name(req.name())
                        .category(req.category())
                        .description(req.description())
                        .build()
        );
        // 인기 카운트 레코드 생성
        popularityRepository.save(StorePopularity.builder().store(saved).count(0).build());
        return saved;
    }

    @Transactional
    public Store update(Long id, StoreUpdateRequest req) {
        Store s = findById(id);
        if (req.name() != null) s.setName(req.name());
        if (req.category() != null) s.setCategory(req.category());
        if (req.description() != null) s.setDescription(req.description());
        return s;
    }

    @Transactional
    public void delete(Long id) {
        storeRepository.delete(findById(id));
    }

    public List<Store> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return List.of();
        return storeRepository.search(keyword.trim());
    }

    /** 검색/클릭 등 이벤트 시 count 증가 */
    @Transactional
    public void increasePopularity(Long storeId, int delta) {
        Store store = findById(storeId);
        StorePopularity p = popularityRepository.findByStore(store)
                .orElseGet(() -> popularityRepository.save(
                        StorePopularity.builder().store(store).count(0).build()
                ));
        p.setCount(p.getCount() + Math.max(delta, 1));
    }

    @Transactional(readOnly = true)
    public List<PopularStoreResponse> topPopular(int limit) {
        return popularityRepository.findTop10ByOrderByCountDesc().stream()
                .limit(limit)
                .map(p -> new PopularStoreResponse(
                        p.getStore().getId(),
                        p.getStore().getName(),
                        p.getStore().getCategory(),
                        p.getCount()
                ))
                .toList();
    }

    // ✅ 추가: 시장 이름 like 검색 (예: "모란시장")
    @Transactional(readOnly = true)
    public List<Store> findByMarketNameLike(String marketName) {
        return storeRepository.findByMarketNameLike(marketName);
    }
}
