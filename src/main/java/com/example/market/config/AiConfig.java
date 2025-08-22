package com.example.market.config;

import com.example.market.ai.MarketAssistant;
import com.example.market.ai.StoreTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Spring AI
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;

@Configuration
public class AiConfig {

    // Spring AI용 (기존)
    private final OpenAiChatModel springAiChatModel;

    // 🔑 Spring 설정에서 같은 키를 재사용 (application.yml 의 spring.ai.openai.api-key)
    @Value("${spring.ai.openai.api-key}")
    private String openaiApiKey;

    public AiConfig(OpenAiChatModel springAiChatModel) {
        this.springAiChatModel = springAiChatModel;
    }

    @Bean
    public ChatClient chatClient() {
        return ChatClient.create(springAiChatModel);
    }

    @Bean
    public MarketAssistant marketAssistant(StoreTools storeTools) {
        // LangChain4j OpenAI 모델
        var lc4jModel = dev.langchain4j.model.openai.OpenAiChatModel.builder()
                .apiKey(openaiApiKey)         // ✅ System.getenv 대신 같은 프로퍼티 사용
                .modelName("gpt-4o")
                .build();

        // 0.35.0 호환: builder 체인
        return dev.langchain4j.service.AiServices
                .builder(com.example.market.ai.MarketAssistant.class)
                .chatLanguageModel(lc4jModel)
                .tools(storeTools)
                .build();
    }
}



