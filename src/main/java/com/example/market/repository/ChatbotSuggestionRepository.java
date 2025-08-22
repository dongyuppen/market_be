package com.example.market.repository;

import com.example.market.entity.ChatbotSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatbotSuggestionRepository extends JpaRepository<ChatbotSuggestion, Long> {
    List<ChatbotSuggestion> findAllByPinnedTrueOrderByClicksDescIdAsc();
    List<ChatbotSuggestion> findAllByCategoryOrderByClicksDescIdAsc(String category);
}
