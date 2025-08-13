package com.example.market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopularityService {

    private final StringRedisTemplate redis;

    @Value("${app.popularity.redis-key-prefix}")
    private String keyPrefix;

    public String key(Long marketId){ return keyPrefix + ":" + marketId; }

    /** 실시간 증가 (search/recommend 발생 시 호출) */
    public void incr(Long marketId, Long shopId, double delta){
        redis.opsForZSet().incrementScore(key(marketId), shopId.toString(), delta);
    }

    /** 인기 상위 N 조회 (옵션: 카테고리 필터는 상위 뽑은 뒤 메모리에서 걸러도 됨) */
    public List<Long> topN(Long marketId, int limit){
        Set<ZSetOperations.TypedTuple<String>> set =
                redis.opsForZSet().reverseRangeWithScores(key(marketId), 0, limit-1);
        if(set==null) return List.of();
        return set.stream().map(t -> Long.valueOf(t.getValue())).collect(Collectors.toList());
    }

    /** 전체 초기화 후 일괄 set (스케줄러에서 사용) */
    public void rebuild(Long marketId, Map<Long, Long> shopScores){
        String k = key(marketId);
        redis.delete(k);
        if(shopScores.isEmpty()) return;
        shopScores.forEach((shopId, score) ->
                redis.opsForZSet().add(k, shopId.toString(), score.doubleValue())
        );
    }
}