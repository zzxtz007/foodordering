TRUNCATE banner;
TRUNCATE consumer;
TRUNCATE dining_hall;
TRUNCATE feedback;
TRUNCATE food;
TRUNCATE food_category;
TRUNCATE global_notice;
TRUNCATE operator;
DROP TABLE operator_id_seq;
TRUNCATE `order`;
TRUNCATE ordered_food;
TRUNCATE recommended_food;
TRUNCATE stall;
DROP TABLE stall_id_seq;

-- 运营方 ID 序列表
CREATE TABLE operator_id_seq
(
  id INT PRIMARY KEY AUTO_INCREMENT
);

-- 商户 ID 序列表
CREATE TABLE stall_id_seq
(
  id INT PRIMARY KEY AUTO_INCREMENT
);

-- 运营账户
-- 密码为 admin
INSERT INTO operator (username, salt, pwd_hash, role)
VALUES ('admin', 'nC4Ci9lseC8N780aHjC8BZbeCQ54uOln',
        '9e69680d4b60eea7b825811b0f32e8d75bcd72fdbe659656e8855eecae1c0edb', 1);
