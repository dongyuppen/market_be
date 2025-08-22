package com.example.market.dto;

import com.example.market.entity.ChatbotSuggestion;

public record SuggestionDto(Long id, String text, String category, int clicks) {
    public static SuggestionDto from(ChatbotSuggestion s) {
        return new SuggestionDto(s.getId(), s.getText(), s.getCategory(), s.getClicks());
    }
}

