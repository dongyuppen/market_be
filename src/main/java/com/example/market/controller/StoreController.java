package com.example.market.controller;

import com.example.market.dto.PopularStoreResponse;
import com.example.market.dto.StoreCreateRequest;
import com.example.market.dto.StoreResponse;
import com.example.market.dto.StoreUpdateRequest;
import com.example.market.entity.Store;
import com.example.market.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    // 가게 전체 조회 (시장별)
    @GetMapping
    public List<StoreResponse> findByMarket(@RequestParam Long marketId) {
        return storeService.findByMarket(marketId).stream()
                .map(StoreResponse::from)
                .toList();
    }

    // 가게 단일 조회
    @GetMapping("/{id}")
    public StoreResponse findOne(@PathVariable Long id) {
        return StoreResponse.from(storeService.findById(id));
    }

    // 가게 등록 (관리자용)
    @PostMapping
    public Store create(@Valid @RequestBody StoreCreateRequest req) {
        return storeService.create(req);
    }

    // 가게 수정 (관리자용)
    @PutMapping("/{id}")
    public Store update(@PathVariable Long id, @RequestBody StoreUpdateRequest req) {
        return storeService.update(id, req);
    }

    // 가게 삭제 (관리자용)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        storeService.delete(id);
    }

    // 가게 검색 (키워드 기반) + 검색된 각 가게에 인기 카운트 +1
    @GetMapping("/search")
    public List<Store> search(@RequestParam String keyword) {
        List<Store> result = storeService.search(keyword);
        result.forEach(s -> storeService.increasePopularity(s.getId(), 1));
        return result;
    }

    // 실시간 인기 가게 랭킹 (검색/추천 카운트 기준)
    @GetMapping("/popular")
    public List<PopularStoreResponse> popular() {
        return storeService.topPopular(10);
    }
}