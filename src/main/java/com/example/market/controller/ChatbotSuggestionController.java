package com.example.market.controller;

import com.example.market.ai.MarketAssistant;
import com.example.market.dto.SuggestionDto;
import com.example.market.service.ChatbotSuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotSuggestionController {

    private final ChatbotSuggestionService suggestionService;
    private final MarketAssistant marketAssistant; // LangChain4j assistant (이미 있음)

    /** ① 추천 질문 목록 */
    @GetMapping("/suggestions")
    public List<SuggestionDto> suggestions(@RequestParam(required = false) String category) {
        return suggestionService.getSuggestions(category);
    }

    /** ② 추천 질문 클릭 → 질문 실행까지 한 번에 */
    @PostMapping("/ask-suggestion/{id}")
    public Map<String, Object> askFromSuggestion(@PathVariable Long id) {
        String text = suggestionService.getTextById(id);
        if (text == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Suggestion not found");
        }
        suggestionService.bumpClick(id);
        String answer = marketAssistant.answer(text); // LangChain4j 경로
        return Map.of("question", text, "answer", answer);
    }

    /** (선택) ③ 클릭 카운트만 올리고, 실제 질문은 프론트에서 /ask-lc4j로 보낼 때 */
    @PostMapping("/suggestions/{id}/click")
    public void onlyBumpClick(@PathVariable Long id) {
        suggestionService.bumpClick(id);
    }
}
