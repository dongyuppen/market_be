package com.example.market.controller;

import com.example.market.domain.Shop;
import com.example.market.dto.ShopDto;
import com.example.market.dto.PopularShopDto;
import com.example.market.repository.ShopRepository;
import com.example.market.service.PopularityService;
import com.example.market.service.ShopService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ShopController {

    private final ShopService shopService;
    private final PopularityService popularityService;
    private final ShopRepository shopRepository;

    @GetMapping("/markets/{marketId}/shops")
    public List<ShopDto> list(
            @PathVariable Long marketId,
            @RequestParam(required=false) String category,
            @RequestParam(required=false) String keyword
    ){
        return shopService.search(marketId, category, keyword).stream()
                .map(ShopDto::from).collect(Collectors.toList());
    }

    @GetMapping("/shops/{shopId}")
    public ShopDto detail(@PathVariable Long shopId){
        Shop s = shopService.get(shopId);
        return ShopDto.from(s);
    }

    @GetMapping("/markets/{marketId}/shops/popular")
    public List<PopularShopDto> popular(
            @PathVariable Long marketId,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int limit
    ){
        List<Long> ids = popularityService.topN(marketId, limit);
        Map<Long, Shop> map = shopRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Shop::getShopId, x->x));
        // Redis 순서를 유지하며 DTO 구성
        return ids.stream()
                .map(id -> new PopularShopDto(id,
                        map.get(id)!=null ? map.get(id).getName() : "unknown", 0L))
                .collect(Collectors.toList());
    }
}