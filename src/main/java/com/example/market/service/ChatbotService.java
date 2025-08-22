package com.example.market.service;

import com.example.market.entity.Market;
import com.example.market.entity.Store;
import com.example.market.repository.MarketRepository;
import com.example.market.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final StoreRepository storeRepository;
    private final MarketRepository marketRepository;
    private final ChatClient chatClient;

    // 동의어/오타 보정기
    private final CategoryNormalizer categoryNormalizer;

    /** “짜장면 파는…” 같은 메뉴 질문 우선 처리 + 목록/주소 즉답 → 그 외 AI */
    @Transactional(readOnly = true)
    public String askChatbot(String userMessage) {
        final String question = userMessage == null ? "" : userMessage.trim();

        // A) 시장 목록 즉답
        if (containsAny(question, "어떤 시장", "시장들", "시장 목록", "시장 리스트", "리스트", "뭐 있어")) {
            List<Market> markets = marketRepository.findAll();
            if (markets.isEmpty()) return "아직 등록된 시장이 없어요.";
            String names = markets.stream()
                    .sorted(Comparator.comparing(Market::getName))
                    .map(Market::getName)
                    .collect(Collectors.joining(", "));
            return "성남시 시장 목록: " + names;
        }

        // B) 특정 시장 주소 즉답
        if (containsAny(question, "어디", "주소", "위치")) {
            Optional<Market> target = findMarketNameFromText(question)
                    .or(() -> marketRepository.findByNameContaining(cleanKeyword(question)));
            if (target.isPresent()) {
                Market m = target.get();
                return "📍 " + m.getName() + "의 위치는 " + safe(m.getLocation()) + "입니다.";
            }
            return "질문 속에서 시장 이름을 찾지 못했어요. 예: '남한산성시장 어디 있어?' 처럼 물어봐 주세요.";
        }

        // C) 메뉴/음식 키워드 검색 즉답 (예: 짜장면/칼국수/회/초밥/떡볶이/치킨/분식 등)
        // 1) “(키워드) 파는” 패턴 우선 캡처
        Optional<String> menuKeyword = extractMenuKeyword(question);
        // 2) 못 찾으면 일반 음식 단어 사전 매칭(포괄)
        if (menuKeyword.isEmpty()) {
            menuKeyword = containsFoodWord(question);
        }

        if (menuKeyword.isPresent()) {
            String kw = menuKeyword.get();
            // 시장이 문장에 있으면 시장 한정 검색
            Optional<Market> marketOpt = findMarketNameFromText(question)
                    .or(() -> marketRepository.findByNameContaining(question));
            List<Store> results;
            if (marketOpt.isPresent()) {
                results = storeRepository.searchInMarket(marketOpt.get().getId(), kw);
            } else {
                results = storeRepository.search(kw);
            }

            if (results.isEmpty()) {
                String where = marketOpt.map(m -> " (" + m.getName() + " 기준)").orElse("");
                return "해당 메뉴를 파는 가게를 찾지 못했어요" + where + ". 다른 키워드로도 물어보실래요?";
            }

            String list = results.stream()
                    .limit(10)
                    .map(s -> {
                        String marketName = (s.getMarket() != null ? s.getMarket().getName() : "");
                        String addr = (s.getAddress() == null ? "" : s.getAddress());
                        String hours = (s.getOpenHours() == null ? "" : s.getOpenHours());
                        String tail = StreamJoiner.joinNotBlank(" · ", addr, hours);
                        String head = StreamJoiner.joinNotBlank(" ", s.getName(),
                                marketName.isBlank() ? "" : "(" + marketName + ")");
                        return "• " + StreamJoiner.joinNotBlank(" ", head, tail.isBlank() ? "" : "— " + tail);
                    })
                    .collect(Collectors.joining("\n"));

            return "🔎 '" + kw + "' 파는 가게:\n" + list;
        }

        // D) 모란시장 특화 (카테고리 우선)
        String marketName = extractMarketName(question);
        if (marketName.contains("모란")) {
            Optional<String> normCatOpt = categoryNormalizer.normalize(question);
            if (normCatOpt.isPresent()) {
                String cat = normCatOpt.get();
                Optional<Market> moranOpt = marketRepository.findByNameContaining("모란시장");
                if (moranOpt.isEmpty()) return "모란시장 데이터가 아직 없어요. DB를 확인해주세요.";
                Market moran = moranOpt.get();

                List<Store> stores =
                        storeRepository.findByMarket_IdAndCategoryContainingIgnoreCase(moran.getId(), cat);
                if (stores.isEmpty()) return "모란시장에 **" + cat + "** 카테고리 가게 정보를 찾지 못했어요 😥";

                String list = stores.stream()
                        .map(s -> "• " + s.getName()
                                + (s.getAddress() != null ? " (" + s.getAddress() + ")" : ""))
                        .collect(Collectors.joining("\n"));
                return "🔎 모란시장 **" + cat + "** 가게 목록:\n" + list;
            }

            // 위치 질문이면 주소
            if (containsAny(question, "어디", "위치", "주소")) {
                return marketRepository.findByNameContaining("모란시장")
                        .map(m -> "📍 " + m.getName() + "의 위치는 " + safe(m.getLocation()) + "입니다.")
                        .orElse("❌ '모란시장' 데이터를 찾지 못했어요. DB를 확인해주세요.");
            }

            // 힌트
            return marketRepository.findByNameContaining("모란시장").map(m -> {
                List<Store> all = storeRepository.findByMarket_Id(m.getId());
                List<String> cats = all.stream().map(Store::getCategory).toList();
                String hint = categoryNormalizer.suggestCategories(cats);
                return "모란시장에서 어떤 가게를 찾으시나요? (예: 정육점, 야채가게, 생선가게, 분식…)\n추천 카테고리: " + hint;
            }).orElse("모란시장 데이터가 아직 없어요. DB를 확인해주세요.");
        }

        // E) 일반 시장(모란 제외): 주소 아니면 AI 호출
        if (!marketName.isEmpty()) {
            Optional<Market> marketOpt = marketRepository.findByNameContaining(marketName);
            if (marketOpt.isPresent()) {
                Market market = marketOpt.get();
                if (containsAny(question, "어디", "주소", "위치")) {
                    return "📍 " + market.getName() + "의 위치는 " + safe(market.getLocation()) + "입니다.";
                }
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
                            .user(question)
                            .call()
                            .content();
                } catch (Exception e) {
                    return "⚠️ AI 서버 오류: " + e.getMessage();
                }
            } else {
                return "❌ '" + marketName + "'에 해당하는 시장을 찾을 수 없어요.";
            }
        }

        // F) 시장명 없음 → 전체 컨텍스트로 AI
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
                    .user(question)
                    .call()
                    .content();
        } catch (Exception e) {
            return "⚠️ 현재 AI 서버를 사용할 수 없습니다: " + e.getMessage();
        }
    }

    // ───────────────────────── 헬퍼들 ─────────────────────────

    /** 질문 텍스트에서 시장명을 동적으로 추출 */
    private String extractMarketName(String text) {
        if (text == null || text.isBlank()) return "";
        for (Market m : marketRepository.findAll()) {
            if (text.contains(m.getName())) return m.getName();
        }
        return marketRepository.findByNameContaining(text).map(Market::getName).orElse("");
    }

    /** 질문에 포함된 시장을 DB에서 찾아 반환(정확 매칭 우선) */
    private Optional<Market> findMarketNameFromText(String text) {
        if (text == null) return Optional.empty();
        for (Market m : marketRepository.findAll()) {
            if (text.contains(m.getName())) return Optional.of(m);
        }
        return Optional.empty();
    }

    private boolean containsAny(String text, String... tokens) {
        if (text == null) return false;
        for (String t : tokens) if (text.contains(t)) return true;
        return false;
    }

    private String safe(String s) { return s == null ? "주소 정보가 없습니다" : s; }

    /** 주소/위치 질문에서 불용어를 걷어내기 위한 간단 전처리 */
    private String cleanKeyword(String text) {
        if (text == null) return "";
        // 공백/구두점 제거 + 흔한 조사/불용어 제거
        String cleaned = text
                .replaceAll("[\\s?!.]", "")
                .replace("어디", "")
                .replace("주소", "")
                .replace("위치", "")
                .replace("가", "")
                .replace("는", "")
                .replace("이", "")
                .replace("에", "")
                .replace("요", "")
                .trim();
        return cleaned;
    }

    /** “짜장면 파는 …” 같은 패턴에서 메뉴 키워드 추출 */
    private Optional<String> extractMenuKeyword(String text) {
        if (text == null) return Optional.empty();
        Pattern p = Pattern.compile("([가-힣A-Za-z0-9]+)\\s*파는");
        Matcher m = p.matcher(text);
        if (m.find()) {
            String kw = m.group(1);
            // “식당/가게/곳” 같은 일반어가 걸리면 버림
            if (!List.of("식당","가게","곳","데","집").contains(kw)) {
                return Optional.of(kw);
            }
        }
        return Optional.empty();
    }

    /** 사전에 등록된 음식/메뉴 단어가 있으면 그 단어를 반환 */
    private Optional<String> containsFoodWord(String text) {
        if (text == null) return Optional.empty();
        String[] dict = {
                "짜장면","짬뽕","탕수육","중식","칼국수","국수","냉면",
                "회","초밥","스시","분식","떡볶이","순대","튀김","김밥",
                "치킨","피자","햄버거","빵","베이커리","떡","전","반찬"
        };
        for (String w : dict) {
            if (text.contains(w)) return Optional.of(w);
        }
        return Optional.empty();
    }

    /** 깔끔한 문자열 합치기용 */
    static class StreamJoiner {
        static String joinNotBlank(String sep, String... parts) {
            return Arrays.stream(parts)
                    .filter(s -> s != null && !s.isBlank())
                    .collect(Collectors.joining(sep));
        }
    }
}
