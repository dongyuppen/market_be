package com.example.market.service;

import com.example.market.dto.SuggestionDto;
import com.example.market.entity.ChatbotSuggestion;
import com.example.market.repository.ChatbotSuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatbotSuggestionService {

    private final ChatbotSuggestionRepository repo;

    @Transactional(readOnly = true)
    public List<SuggestionDto> getSuggestions(@Nullable String category) {
        List<ChatbotSuggestion> list = (category == null || category.isBlank())
                ? repo.findAllByPinnedTrueOrderByClicksDescIdAsc()
                : repo.findAllByCategoryOrderByClicksDescIdAsc(category);
        return list.stream().map(SuggestionDto::from).toList();
    }

    @Transactional
    public void bumpClick(Long id) {
        repo.findById(id).ifPresent(s -> {
            s.setClicks(s.getClicks() + 1);
            repo.save(s);
        });
    }

    @Transactional(readOnly = true)
    public String getTextById(Long id) {
        return repo.findById(id)
                .map(ChatbotSuggestion::getText)
                .orElse(null);
    }
}
