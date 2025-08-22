-- data.sql 수정

-- 기존 데이터 초기화
DELETE FROM stores;
DELETE FROM markets;

-- -------------------------------------
-- 성남시 전통시장 (시장 정보)
-- -------------------------------------

INSERT INTO markets (name, description, location)
VALUES ('모란시장', '성남시의 대표 전통시장입니다.', '경기 성남시 중원구 둔촌대로 68');

INSERT INTO markets (name, description, location)
VALUES ('중앙시장', '중앙동에 위치한 지역시장입니다.', '경기 성남시 수정구 수정로 109 중앙시장사거리');

INSERT INTO markets (name, description, location)
VALUES ('단대마트시장', '성남시 전통시장', '경기 성남시 중원구 광명로 289 (금광1동 1-14)');

INSERT INTO markets (name, description, location)
VALUES ('은행골목시장', '성남시 전통시장', '경기 성남시 중원구 산성대로524번길 8 (은행2동 1-1)');

INSERT INTO markets (name, description, location)
VALUES ('성남남한산성시장', '성남시 전통시장', '경기 성남시 중원구 산성대로 526 (은행2동 1-5)');

INSERT INTO markets (name, description, location)
VALUES ('하대원시장', '성남시 전통시장', '경기 성남시 중원구 마지로 118 (하대원동 1-4)');

INSERT INTO markets (name, description, location)
VALUES ('금호시장', '성남시 전통시장', '경기 성남시 분당구 내정로165번길 38 (수내1동 1-7)');

INSERT INTO markets (name, description, location)
VALUES ('동신종합시장', '성남시 전통시장', '경기 성남시 분당구 돌마로 361 (수내1동 1-22)');

INSERT INTO markets (name, description, location)
VALUES ('돌고래시장', '성남시 전통시장', '경기 성남시 분당구 내정로174번길 42 (수내2동 1-7)');

INSERT INTO markets (name, description, location)
VALUES ('코끼리시장', '성남시 전통시장', '경기 성남시 분당구 내정로166번길 7-6 (수내2동 1-7)');

INSERT INTO markets (name, description, location)
VALUES ('분당종합시장', '성남시 전통시장', '경기 성남시 분당구 돌마로366번길 42 (수내3동 1-7)');

INSERT INTO markets (name, description, location)
VALUES ('현대프라자시장', '성남시 전통시장', '경기 성남시 분당구 돌마로 364 (수내3동)');

-- -------------------------------------
-- 모란시장 가게 더미 데이터
-- -------------------------------------

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '한우참맛', '정육점', '축산동 12호', '한우/돼지 생고기 전문', '매일 09:00~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '모란야채마을', '야채가게', '농산물동 5호', '신선 채소/나물', '매일 08:30~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '바다정', '생선가게', '수산동 27호', '제철 생선/회/조개', '화~일 09:00~19:30'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '분식당당', '분식', '먹거리골목 3-2', '떡볶이/순대/튀김', '매일 10:00~21:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '우리빵집', '베이커리', '중앙통로 18호', '소금빵/단팥빵', '매일 09:30~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '푸드정육', '정육점', '축산동 7호', '정육/양념육', '매일 09:00~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '싱그런채소', '야채가게', '농산물동 9호', '제철 채소/과일 소량 판매', '매일 08:30~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '김씨네반찬', '반찬가게', '먹거리골목 10호', '집반찬/나물/전', '매일 10:00~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

-- 모란시장 중식/분식 샘플
INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '홍룡각', '중식당', '먹거리골목 21호', '짜장면/짬뽕/탕수육 전문', '매일 11:00~21:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '춘래춘래', '중식당', '먹거리골목 22호', '간짜장/짬뽕/군만두', '월휴무, 11:00~20:30'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;
-- 모란시장 분식/치킨/회/야채 등 샘플 더미데이터

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '은혜분식', '분식', '분식골목 5호', '떡볶이, 순대, 튀김, 김밥', '매일 10:30~21:30'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '모란치킨타운', '치킨', '먹거리골목 12호', '후라이드/양념치킨 전문', '매일 16:00~24:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '바다회센터', '횟집', '생선코너 8호', '광어, 우럭, 연어회 전문', '매일 11:00~22:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '왕칼국수집', '분식', '먹거리골목 33호', '손칼국수, 보쌈세트', '화~일 11:00~21:00 (월휴무)'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '모란정육점', '정육점', '축산골목 2호', '한우, 삼겹살, 불고기용 고기', '매일 09:00~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '자연야채가게', '야채가게', '채소코너 14호', '신선한 채소와 과일 판매', '매일 08:30~19:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '모란분식왕', '분식', '분식골목 9호', '김밥, 라면, 떡볶이 세트', '매일 10:00~20:30'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '모란순대국', '한식', '먹거리골목 41호', '순대국, 머리고기, 술안주 메뉴', '매일 06:00~22:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '할머니떡집', '떡집', '시장안 102호', '각종 전통 떡과 한과 판매', '매일 09:00~18:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

INSERT INTO stores (market_id, name, category, address, description, open_hours)
SELECT m.id, '빵굽는 사람들', '베이커리', '먹거리골목 55호', '식빵, 케이크, 크로와상', '매일 08:00~20:00'
FROM markets m WHERE m.name LIKE '%모란시장%' LIMIT 1;

