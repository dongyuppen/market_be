package com.example.market.dto;

import com.example.market.domain.Shop;

public record ShopDto(Long shopId, Long marketId, String name, String category,
                      String description, String location, String phone, String imageUrl) {
    public static ShopDto from(Shop s){
        return new ShopDto(
                s.getShopId(), s.getMarket().getMarketId(), s.getName(), s.getCategory(),
                s.getDescription(), s.getLocation(), s.getPhone(), s.getImageUrl()
        );
    }
}