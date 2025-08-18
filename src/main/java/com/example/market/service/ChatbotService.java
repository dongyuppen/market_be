package com.example.market.service;

import com.example.market.entity.Market;
import com.example.market.entity.Store;
import com.example.market.repository.MarketRepository;
import com.example.market.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final StoreRepository storeRepository;
    private final MarketRepository marketRepository;
    private final ChatClient chatClient;

    // 추가: 카테고리 정규화기 (동의어/오타 보정)
    private final CategoryNormalizer categoryNormalizer;

    public String askChatbot(String userMessage) {
        // 1) 시장명 추출
        String marketName = extractMarketName(userMessage);

        // ---------- 모란시장 한정 카테고리 분기 (카테고리 우선 → 위치 후순위) ----------
        if (marketName != null && !marketName.isEmpty() && marketName.contains("모란")) {

            // 1) 먼저 카테고리부터 판별 (정육점/야채가게/…)
            Optional<String> normCatOpt = categoryNormalizer.normalize(userMessage);
            if (normCatOpt.isPresent()) {
                String cat = normCatOpt.get();

                Optional<Market> moranOpt = marketRepository.findByNameContaining("모란시장");
                if (moranOpt.isEmpty()) {
                    return "모란시장 데이터가 아직 없어요. DB를 확인해주세요.";
                }
                Market moran = moranOpt.get();

                // 카테고리 매칭 → 해당 카테고리 가게 목록 + 주소
                List<Store> stores =
                        storeRepository.findByMarket_IdAndCategoryContainingIgnoreCase(moran.getId(), cat);

                if (stores.isEmpty()) {
                    return "모란시장에 **" + cat + "** 카테고리 가게 정보를 찾지 못했어요 😥";
                }

                String list = stores.stream()
                        .map(s -> "• " + s.getName()
                                + (s.getAddress() != null ? " (" + s.getAddress() + ")" : ""))
                        .collect(Collectors.joining("\n"));

                return "🔎 모란시장 **" + cat + "** 가게 목록:\n" + list;
            }

            // 2) 카테고리가 없을 때만 '어디/주소/위치'를 시장 주소로 응답
            if (containsAny(userMessage, "어디", "위치", "주소")) {
                Optional<Market> moranOpt = marketRepository.findByNameContaining("모란시장");
                if (moranOpt.isPresent()) {
                    Market moran = moranOpt.get();
                    return "📍 " + moran.getName() + "의 위치는 " + moran.getLocation() + "입니다.";
                } else {
                    return "❌ '모란시장' 데이터를 찾지 못했어요. DB를 확인해주세요.";
                }
            }

            // 3) 둘 다 아니면 카테고리 힌트
            Optional<Market> moranOpt = marketRepository.findByNameContaining("모란시장");
            if (moranOpt.isPresent()) {
                Market moran = moranOpt.get();
                List<Store> all = storeRepository.findByMarket_Id(moran.getId());
                List<String> cats = all.stream().map(Store::getCategory).toList();
                String hint = categoryNormalizer.suggestCategories(cats);
                return "모란시장에서 어떤 가게를 찾으시나요? (예: 정육점, 야채가게, 생선가게, 분식…)\n"
                        + "추천 카테고리: " + hint;
            }
            // ---------- 모란시장 분기 끝 ----------
        }

        // 2) 시장명이 추출되고, 모란시장이 아닌 일반 케이스
        if (marketName != null && !marketName.isEmpty()) {
            Optional<Market> marketOpt = marketRepository.findByNameContaining(marketName);
            if (marketOpt.isPresent()) {
                Market market = marketOpt.get();

                // 2-1) 위치/주소 문의면 바로 응답
                if (containsAny(userMessage, "어디", "주소", "위치")) {
                    return "📍 " + market.getName() + "의 위치는 " + market.getLocation() + "입니다.";
                }

                // 2-2) 시장 내 가게들을 컨텍스트로 전달 (기존 동작 유지)
                List<Store> stores = storeRepository.findByMarket_Id(market.getId());
                StringBuilder context = new StringBuilder("[" + market.getName() + "] 내 가게 목록:\n");
                for (Store s : stores) {
                    context.append("- ").append(s.getName())
                            .append(" (").append(s.getCategory()).append("): ")
                            .append(s.getDescription() == null ? "" : s.getDescription())
                            .append("\n");
                }

                try {
                    return chatClient.prompt()
                            .system("너는 시장 추천 도우미야. 사용자 요청에 따라 가게를 추천해줘.")
                            .system(context.toString())
                            .user(userMessage)
                            .call()
                            .content();
                } catch (Exception e) {
                    return "⚠️ AI 서버 오류: " + e.getMessage();
                }
            } else {
                return "❌ '" + marketName + "'에 해당하는 시장을 찾을 수 없어요.";
            }
        }

        // 3) 시장명이 없을 때 → 전체 가게 목록 기반 응답 (기존 동작 유지)
        List<Store> allStores = storeRepository.findAll();
        StringBuilder context = new StringBuilder("전체 시장 가게 목록:\n");
        for (Store s : allStores) {
            context.append("- ").append(s.getName())
                    .append(" (").append(s.getCategory()).append("): ")
                    .append(s.getDescription() == null ? "" : s.getDescription())
                    .append("\n");
        }

        try {
            return chatClient.prompt()
                    .system("너는 시장 추천 도우미야. 사용자 요청에 따라 가게를 추천해줘.")
                    .system(context.toString())
                    .user(userMessage)
                    .call()
                    .content();
        } catch (Exception e) {
            return "⚠️ 현재 AI 서버를 사용할 수 없습니다: " + e.getMessage();
        }
    }

    /**
     * 사용자 메시지에서 시장명 추출하는 함수 (리스트 기반 키워드 매칭)
     */
    private String extractMarketName(String text) {
        String[] knownMarkets = {"모란시장", "중앙시장", "신흥시장", "상대원시장"};
        for (String market : knownMarkets) {
            if (text != null && text.contains(market)) {
                return market;
            }
        }
        return "";
    }

    private boolean containsAny(String text, String... tokens) {
        if (text == null) return false;
        for (String t : tokens) {
            if (text.contains(t)) return true;
        }
        return false;
    }
}


