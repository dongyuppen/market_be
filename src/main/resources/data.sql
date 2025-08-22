-- =======================================
-- 스키마 보정: chatbot_suggestion 재생성
-- =======================================

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS chatbot_suggestion;

CREATE TABLE chatbot_suggestion (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    text VARCHAR(255) NOT NULL,
                                    category VARCHAR(50) NULL,
                                    pinned BOOLEAN NOT NULL DEFAULT TRUE,
                                    clicks INT NOT NULL DEFAULT 0
);

-- 기존 데이터 초기화 (자식 → 부모 순서)
DELETE FROM store_popularity;
DELETE FROM stores;
DELETE FROM markets;
DELETE FROM chatbot_suggestion;

SET FOREIGN_KEY_CHECKS = 1;


-- =======================================
-- 시장 데이터 (부모)
-- =======================================

INSERT INTO markets (id, name, description, location)
VALUES (1, '모란시장', '성남시의 대표 전통시장입니다.', '경기 성남시 중원구 둔촌대로 68');

INSERT INTO markets (id, name, description, location)
VALUES (2, '중앙시장', '중앙동에 위치한 지역시장입니다.', '경기 성남시 수정구 수정로 109 중앙시장사거리');

INSERT INTO markets (id, name, description, location)
VALUES (3, '단대마트시장', '성남시 전통시장', '경기 성남시 중원구 광명로 289 (금광1동 1-14)');

INSERT INTO markets (id, name, description, location)
VALUES (4, '은행골목시장', '성남시 전통시장', '경기 성남시 중원구 산성대로524번길 8 (은행2동 1-1)');

INSERT INTO markets (id, name, description, location)
VALUES (5, '성남남한산성시장', '성남시 전통시장', '경기 성남시 중원구 산성대로 526 (은행2동 1-5)');

INSERT INTO markets (id, name, description, location)
VALUES (6, '하대원시장', '성남시 전통시장', '경기 성남시 중원구 마지로 118 (하대원동 1-4)');

INSERT INTO markets (id, name, description, location)
VALUES (7, '금호시장', '성남시 전통시장', '경기 성남시 분당구 내정로165번길 38 (수내1동 1-7)');

INSERT INTO markets (id, name, description, location)
VALUES (8, '동신종합시장', '성남시 전통시장', '경기 성남시 분당구 돌마로 361 (수내1동 1-22)');

INSERT INTO markets (id, name, description, location)
VALUES (9, '돌고래시장', '성남시 전통시장', '경기 성남시 분당구 내정로174번길 42 (수내2동 1-7)');

INSERT INTO markets (id, name, description, location)
VALUES (10, '코끼리시장', '성남시 전통시장', '경기 성남시 분당구 내정로166번길 7-6 (수내2동 1-7)');

INSERT INTO markets (id, name, description, location)
VALUES (11, '분당종합시장', '성남시 전통시장', '경기 성남시 분당구 돌마로366번길 42 (수내3동 1-7)');

INSERT INTO markets (id, name, description, location)
VALUES (12, '현대프라자시장', '성남시 전통시장', '경기 성남시 분당구 돌마로 364 (수내3동)');

-- =======================================
-- 모란시장 가게 데이터 (자식)
-- =======================================

INSERT INTO stores (id, market_id, name, category, address, description, open_hours) VALUES
                                                                                         (100, 1, '한우참맛', '정육점', '축산동 12호', '한우/돼지 생고기 전문', '매일 09:00~20:00'),
                                                                                         (101, 1, '모란야채마을', '야채가게', '농산물동 5호', '신선 채소/나물', '매일 08:30~20:00'),
                                                                                         (102, 1, '바다정', '생선가게', '수산동 27호', '제철 생선/회/조개', '화~일 09:00~19:30'),
                                                                                         (103, 1, '분식당당', '분식', '먹거리골목 3-2', '떡볶이/순대/튀김', '매일 10:00~21:00'),
                                                                                         (104, 1, '우리빵집', '베이커리', '중앙통로 18호', '소금빵/단팥빵', '매일 09:30~20:00'),
                                                                                         (105, 1, '푸드정육', '정육점', '축산동 7호', '정육/양념육', '매일 09:00~20:00'),
                                                                                         (106, 1, '싱그런채소', '야채가게', '농산물동 9호', '제철 채소/과일 소량 판매', '매일 08:30~20:00'),
                                                                                         (107, 1, '김씨네반찬', '반찬가게', '먹거리골목 10호', '집반찬/나물/전', '매일 10:00~20:00'),
                                                                                         (108, 1, '홍룡각', '중식당', '먹거리골목 21호', '짜장면/짬뽕/탕수육 전문', '매일 11:00~21:00'),
                                                                                         (109, 1, '춘래춘래', '중식당', '먹거리골목 22호', '간짜장/짬뽕/군만두', '월휴무, 11:00~20:30'),
                                                                                         (110, 1, '은혜분식', '분식', '분식골목 5호', '떡볶이, 순대, 튀김, 김밥', '매일 10:30~21:30'),
                                                                                         (111, 1, '모란치킨타운', '치킨', '먹거리골목 12호', '후라이드/양념치킨 전문', '매일 16:00~24:00'),
                                                                                         (112, 1, '바다회센터', '횟집', '생선코너 8호', '광어, 우럭, 연어회 전문', '매일 11:00~22:00'),
                                                                                         (113, 1, '왕칼국수집', '분식', '먹거리골목 33호', '손칼국수, 보쌈세트', '화~일 11:00~21:00 (월휴무)'),
                                                                                         (114, 1, '모란정육점', '정육점', '축산골목 2호', '한우, 삼겹살, 불고기용 고기', '매일 09:00~20:00'),
                                                                                         (115, 1, '자연야채가게', '야채가게', '채소코너 14호', '신선한 채소와 과일 판매', '매일 08:30~19:00'),
                                                                                         (116, 1, '모란분식왕', '분식', '분식골목 9호', '김밥, 라면, 떡볶이 세트', '매일 10:00~20:30'),
                                                                                         (117, 1, '모란순대국', '한식', '먹거리골목 41호', '순대국, 머리고기, 술안주 메뉴', '매일 06:00~22:00'),
                                                                                         (118, 1, '할머니떡집', '떡집', '시장안 102호', '각종 전통 떡과 한과 판매', '매일 09:00~18:00'),
                                                                                         (119, 1, '빵굽는 사람들', '베이커리', '먹거리골목 55호', '식빵, 케이크, 크로와상', '매일 08:00~20:00');

-- =======================================
-- 인기 카운트 초기화 (store_popularity)
-- =======================================

INSERT INTO store_popularity (store_id, count)
SELECT s.id, 0 FROM stores s WHERE s.market_id = 1;

-- =======================================
-- 추천 질문 초기 데이터
-- =======================================

INSERT INTO chatbot_suggestion (text, category, pinned) VALUES
                                                            ('모란시장에 뭐 뭐 있어?', '시작하기', TRUE),
                                                            ('모란시장 카테고리 추천해줘', '시작하기', TRUE),
                                                            ('모란시장 칼국수 맛집 추천', '음식', TRUE),
                                                            ('모란시장 TOP 5 가게 알려줘', '인기', TRUE),
                                                            ('분식집 영업시간 알려줘', '운영시간', TRUE);
