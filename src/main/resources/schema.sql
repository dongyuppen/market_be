-- ====== stores.address 없으면 추가 ======
SET @col_exists := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'stores'
    AND COLUMN_NAME = 'address'
);
SET @ddl := IF(@col_exists = 0,
  'ALTER TABLE stores ADD COLUMN address VARCHAR(255) NULL',
  'SELECT 1'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ====== stores.open_hours 없으면 추가 ======
SET @col_exists := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'stores'
    AND COLUMN_NAME = 'open_hours'
);
SET @ddl := IF(@col_exists = 0,
  'ALTER TABLE stores ADD COLUMN open_hours VARCHAR(120) NULL',
  'SELECT 1'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
