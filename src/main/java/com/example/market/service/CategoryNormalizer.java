package com.example.market.service;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryNormalizer {
    private final Map<String, String> norm = new LinkedHashMap<>();

    public CategoryNormalizer() {
        // 표준 라벨과 동의어/오타
        putAll("정육점", List.of("정육점", "고기", "고기집", "육류", "축산", "정육"));
        putAll("야채가게", List.of("야채가게", "야채", "채소", "청과", "야체")); // '야체' 오타 포함
        putAll("생선가게", List.of("생선가게", "수산", "수산물", "회", "생선"));
        putAll("분식", List.of("분식", "분식집", "떡볶이", "김밥", "튀김"));
        putAll("반찬가게", List.of("반찬", "반찬가게"));
        putAll("베이커리", List.of("베이커리", "빵집", "제과", "제빵"));
    }

    private void putAll(String canonical, List<String> keys) {
        for (String k : keys) norm.put(k.toLowerCase(), canonical);
    }

    /** 사용자의 자유로운 표현을 표준 카테고리로 맵핑 */
    public Optional<String> normalize(String text) {
        if (text == null) return Optional.empty();
        String q = text.toLowerCase();
        for (Map.Entry<String, String> e : norm.entrySet()) {
            if (q.contains(e.getKey())) {
                return Optional.of(e.getValue());
            }
        }
        return Optional.empty();
    }

    /** 추천 카테고리 문자열 생성 (중복 제거, 입력 순서 유지) */
    public String suggestCategories(Collection<String> categories) {
        LinkedHashSet<String> set = new LinkedHashSet<>(categories);
        return String.join(", ", set);
    }
}

