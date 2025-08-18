package com.example.market.service;

import com.example.market.entity.Store;
import com.example.market.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final StoreRepository storeRepository;
    private final ChatClient chatClient;

    public String askChatbot(String userMessage) {
        // DB에서 시장/가게 정보 가져오기
        List<Store> stores = storeRepository.findAll();
        StringBuilder context = new StringBuilder("시장 가게 목록:\n");
        for (Store s : stores) {
            context.append("- ").append(s.getName())
                    .append(" (").append(s.getCategory()).append("): ")
                    .append(s.getDescription()).append("\n");
        }

        // ✅ 여기서 OpenAI 호출을 try-catch로 감쌈
        try {
            return chatClient.prompt()
                    .system("너는 시장 추천 도우미야. 사용자 요청에 따라 가게를 추천해줘.")
                    .system(context.toString())
                    .user(userMessage)
                    .call()
                    .content();
        } catch (Exception e) {
            // 예외 발생 시 안전하게 안내 메시지 반환
            return "⚠️ 현재 AI 서버를 사용할 수 없습니다: " + e.getMessage();
        }
    }
}