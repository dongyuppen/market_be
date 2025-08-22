package com.example.market.ai;

import com.example.market.dto.PopularStoreResponse;
import com.example.market.entity.Store;
import com.example.market.service.StoreService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreTools {

    private final StoreService storeService;

    public StoreTools(StoreService storeService) {
        this.storeService = storeService;
    }

    // ✅ 인기 가게 Top N
    @Tool("인기 상위 N개의 가게를 반환한다")
    public List<PopularStoreResponse> topStores(int topN) {
        return storeService.topPopular(topN);
    }

    // ✅ 키워드 검색 + 각 결과 인기 카운트 +1
    @Tool("키워드로 가게를 검색하고, 검색된 각 가게의 인기 카운트를 1 증가시킨다")
    public int searchAndBump(String keyword) {
        List<Store> result = storeService.search(keyword);
        result.forEach(s -> storeService.increasePopularity(s.getId(), 1));
        return result.size();
    }
}
