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

    // ✅ 시장 이름으로 가게 목록 조회 (상세: 설명 + 영업시간까지)
    @Tool("주어진 시장 이름에 해당하는 모든 가게를 자세히 나열한다. 예: '모란시장'")
    public List<String> listStoresByMarket(String marketName) {
        return storeService.findByMarketNameLike(marketName).stream()
                .map(s -> s.getName()
                        + (s.getCategory() != null ? " · " + s.getCategory() : "")
                        + (s.getAddress() != null ? " (" + s.getAddress() + ")" : "")
                        + (s.getDescription() != null ? " - " + s.getDescription() : "")
                        + (s.getOpenHours() != null ? " ⏰ " + s.getOpenHours() : "")
                )
                .toList();
    }

    // ✅ 특정 가게 상세 조회 (이름 일부 매칭 허용)
    @Tool("가게 이름으로 상세 정보를 보여준다. 이름 일부만 입력해도 된다.")
    public String storeDetails(String storeName) {
        return storeService.search(storeName).stream()
                .findFirst()
                .map(s -> s.getName()
                        + (s.getCategory() != null ? " · " + s.getCategory() : "")
                        + (s.getAddress() != null ? " (" + s.getAddress() + ")" : "")
                        + (s.getDescription() != null ? " - " + s.getDescription() : "")
                        + (s.getOpenHours() != null ? " ⏰ " + s.getOpenHours() : "")
                )
                .orElse("해당 가게를 찾을 수 없습니다.");
    }
}
