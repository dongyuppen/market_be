package com.example.market.controller;

import com.example.market.dto.RecommendationCreateRequest;
import com.example.market.entity.Recommendation;
import com.example.market.service.RecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    // 챗봇이 "이 가게를 추천했어요" 라는 로그 저장
    @PostMapping
    public Recommendation create(@Valid @RequestBody RecommendationCreateRequest req) {
        return recommendationService.create(req);
    }
}