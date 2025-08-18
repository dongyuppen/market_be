-- data.sql 수정

DELETE FROM stores;
DELETE FROM markets;


INSERT INTO markets (name, description, location)
VALUES ('모란시장', '성남시의 대표 전통시장입니다.', '경기 성남시 중원구 둔촌대로 68');

INSERT INTO markets (name, description, location)
VALUES ('중앙시장', '중앙동에 위치한 지역시장입니다.', '경기 성남시 수정구 수정로 109 중앙시장사거리');

-- 모란시장(없으면 먼저 markets insert)
-- 예: (id는 자동증가 사용 시 제외)
-- INSERT INTO markets (name, description, location)
-- VALUES ('모란시장', '성남 최대 전통시장', '경기도 성남시 수정구 태평동 일대');

-- 모란시장 가게 더미
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

