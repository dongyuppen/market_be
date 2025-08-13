package com.example.market.scheduler;

import com.example.market.repository.*;
import com.example.market.service.PopularityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PopularityRebuildScheduler {

    private final RecommendationLogRepository recRepo;
    private final ShopSearchLogRepository searchRepo;
    private final PopularityService popularityService;

    @Value("${app.popularity.recent-days}")
    private int recentDays;

    // 매시간 재빌드(운영은 주기 조정)
    @Scheduled(cron = "0 0 * * * *")
    public void rebuildAll() {
        LocalDateTime from = LocalDateTime.now().minusDays(recentDays);

        // 전체 시장별 점수 집계 (단순화: 시장별 분리는 rebuild에서 키가 시장별이므로 shopId->marketId join 필요하지만
        // 실무에선 네이티브 쿼리/뷰로 한 방에 뽑는 걸 권장. 여기선 메모리 결합 생략하고 단일 시장 운영 가정 시 사용.)
        // 다시장 지원하려면 ShopRepo를 통해 shopId->marketId 맵을 만들어 나눠 담으세요.
        Map<Long, Long> scoreByShop = new HashMap<>();

        searchRepo.countSince(from).forEach(arr -> {
            Long shopId = (Long) arr[0]; Long cnt = (Long) arr[1];
            scoreByShop.merge(shopId, cnt, Long::sum);
        });
        recRepo.countSince(from).forEach(arr -> {
            Long shopId = (Long) arr[0]; Long cnt = (Long) arr[1];
            scoreByShop.merge(shopId, cnt, Long::sum);
        });

        // 단일 시장만 운영한다면 marketId=1 고정:
        Long marketId = 1L;
        popularityService.rebuild(marketId, scoreByShop);
    }
}