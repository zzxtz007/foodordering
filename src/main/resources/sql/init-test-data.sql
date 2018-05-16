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

-- Banner
INSERT INTO banner (image_url, link, insert_user, update_user)
VALUES ('1.jpg', 'http://www.google.com', 'o_1', 'o_1');
INSERT INTO banner (image_url, link, insert_user, update_user)
VALUES ('2.jpg', 'http://www.baidu.com', 'o_1', 'o_1');
INSERT INTO banner (image_url, link, insert_user, update_user)
VALUES ('3.jpg', 'http://www.baidu.com', 'o_1', 'o_1');
INSERT INTO banner (image_url, link, insert_user, update_user)
VALUES ('4.jpg', 'http://www.sina.com.cn', 'o_1', 'o_1');
INSERT INTO banner (image_url, link, insert_user, update_user)
VALUES ('5.png', 'http://www.baidu.com', 'o_1', 'o_1');

-- 顾客
INSERT INTO consumer (id, is_member, name, student_id)
VALUES ('openid_000000000000000000001', TRUE, 'Aさん', '170601028');
INSERT INTO consumer (id, is_member, name, student_id)
VALUES ('openid_000000000000000000002', TRUE, 'Bさん', '170601024');
INSERT INTO consumer (id, is_member, name, student_id)
VALUES ('openid_000000000000000000003', TRUE, 'Cさん', '170601021');
INSERT INTO consumer (id, is_member, name, student_id)
VALUES ('openid_000000000000000000004', TRUE, 'Dさん', '170601001');
INSERT INTO consumer (id, is_member, name, student_id)
VALUES ('openid_000000000000000000005', FALSE, NULL, NULL);
INSERT INTO consumer (id, is_member, name, student_id)
VALUES ('openid_000000000000000000006', FALSE, NULL, NULL);

-- 食堂
INSERT INTO dining_hall (id, name, image_url, start_time, end_time, introduction, insert_user, update_user)
VALUES
  (1, '北一食堂', '1.jpg', '6:00:00', '21:00:00', '北一食堂介绍', 'o_1', 'o_1');
INSERT INTO dining_hall (id, name, image_url, start_time, end_time, introduction, insert_user, update_user)
VALUES
  (2, '北二食堂', '2.jpg', '6:00:00', '21:00:00', '北二食堂介绍', 'o_1', 'o_1');
INSERT INTO dining_hall (id, name, image_url, start_time, end_time, introduction, insert_user, update_user)
VALUES
  (3, '南一食堂', '3.jpg', '6:00:00', '21:00:00', '南一食堂介绍', 'o_1', 'o_1');
INSERT INTO dining_hall (id, name, image_url, start_time, end_time, introduction, insert_user, update_user)
VALUES
  (4, '南二食堂', '4.jpg', '6:00:00', '21:30:00', '南二食堂介绍', 'o_1', 'o_1');

-- 反馈
INSERT INTO feedback (content, insert_user, update_user)
VALUES ('这是一条短反馈', 'openid_000000000000000000001', 'openid_000000000000000000001');
INSERT INTO feedback (content, insert_user, update_user)
VALUES ('这是一条短反馈2', 'openid_000000000000000000004', 'openid_000000000000000000004');
INSERT INTO feedback (content, insert_user, update_user)
VALUES ('这是一条短反馈3', 'openid_000000000000000000002', 'openid_000000000000000000002');
INSERT INTO feedback (content, insert_user, update_user)
VALUES (
  '这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈',
  'openid_000000000000000000001', 'openid_000000000000000000001');
INSERT INTO feedback (content, insert_user, update_user)
VALUES ('这是一条长反馈4', 'openid_000000000000000000005', 'openid_000000000000000000005');
INSERT INTO feedback (content, insert_user, update_user)
VALUES (
  '这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈2',
  'openid_000000000000000000003', 'openid_000000000000000000003');
INSERT INTO feedback (content, insert_user, update_user)
VALUES (
  '这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈这是一条长反馈3',
  'openid_000000000000000000004', 'openid_000000000000000000004');

-- 菜品
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (1, '菜品1', '1.jpg', 1, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 50, 125, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (2, '菜品2', '2.jpg', 1, '美味しい', TRUE, 0.01, 0.02, NULL, NULL, NULL, NULL, 22, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (3, '菜品3', '3.jpg', 1, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 50, 40, 386, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (4, '菜品4', '4.jpg', 1, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 50, 17, 343, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (5, '菜品5', '5.jpg', 2, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 100, 28, 219, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (6, '菜品6', '6.jpg', 2, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 20, 180, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (7, '菜品7', '7.jpg', 3, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 69, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (8, '菜品8', '8.jpg', 3, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 259, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (9, '菜品9', '9.jpg', 4, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 90, 12, 21, '懒得填了', '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (10, '菜品10', '10.jpg', 4, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 35, 136, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (11, '菜品11', '11.jpg', 5, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 235, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (12, '菜品12', '12.jpg', 6, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 26, 294, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (13, '菜品13', '13.jpg', 6, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 232, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (14, '菜品14', '14.jpg', 6, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 90, 42, 170, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (15, '菜品15', '15.jpg', 6, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 248, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (16, '菜品16', '16.jpg', 7, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 100, 23, 357, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (17, '菜品17', '17.jpg', 7, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 398, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (18, '菜品18', '18.jpg', 8, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 80, 17, 221, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (19, '菜品19', '19.jpg', 8, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 90, 48, 297, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (20, '菜品20', '20.jpg', 8, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 34, 288, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (21, '菜品21', '21.jpg', 8, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 14, 313, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (22, '菜品22', '22.jpg', 9, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 305, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (23, '菜品23', '23.jpg', 9, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 19, 168, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (24, '菜品24', '24.jpg', 9, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 123, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (25, '菜品25', '25.jpg', 9, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 90, 32, 332, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (26, '菜品26', '26.jpg', 10, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 347, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (27, '菜品27', '27.jpg', 10, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 50, 31, 159, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (28, '菜品28', '28.jpg', 10, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 41, 68, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (29, '菜品29', '29.jpg', 11, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 1, 293, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (30, '菜品30', '30.jpg', 11, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 230, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (31, '菜品31', '31.jpg', 11, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 90, 43, 310, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (32, '菜品32', '32.jpg', 12, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 32, 299, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (33, '菜品33', '33.jpg', 12, '美味しい', FALSE, 0.01, 0.01, NULL, NULL, NULL, NULL, 38, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (34, '菜品34', '34.jpg', 12, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 10, 101, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (35, '菜品35', '35.jpg', 12, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 186, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (36, '菜品36', '36.jpg', 12, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 32, 151, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (37, '菜品37', '37.jpg', 13, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 90, 4, 44, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (38, '菜品38', '38.jpg', 13, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 29, 206, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (39, '菜品39', '39.jpg', 13, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 4, 179, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (40, '菜品40', '40.jpg', 14, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 58, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (41, '菜品41', '41.jpg', 14, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 50, 23, 179, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (42, '菜品42', '42.jpg', 14, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 70, 25, 403, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (43, '菜品43', '43.jpg', 14, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 40, 303, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (44, '菜品44', '44.jpg', 15, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 250, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (45, '菜品45', '45.jpg', 15, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 13, 193, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (46, '菜品46', '46.jpg', 16, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 36, 145, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (47, '菜品47', '47.jpg', 16, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 43, 407, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (48, '菜品48', '48.jpg', 17, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 35, 320, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (49, '菜品49', '49.jpg', 17, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 60, 14, 197, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (50, '菜品50', '50.jpg', 18, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 100, 39, 395, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (51, '菜品51', '51.jpg', 18, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 76, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (52, '菜品52', '52.jpg', 18, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 90, 46, 20, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (53, '菜品53', '53.jpg', 19, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 39, 139, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (54, '菜品54', '54.jpg', 20, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 50, 45, 158, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (55, '菜品55', '55.jpg', 20, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 100, 19, 236, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (56, '菜品56', '56.jpg', 21, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 357, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (57, '菜品57', '57.jpg', 21, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 100, 16, 348, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (58, '菜品58', '58.jpg', 21, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 36, 92, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (59, '菜品59', '59.jpg', 22, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 70, 26, 360, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (60, '菜品60', '60.jpg', 22, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 41, 209, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (61, '菜品61', '61.jpg', 22, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 90, 8, 398, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (62, '菜品62', '62.jpg', 23, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 253, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (63, '菜品63', '63.jpg', 23, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 50, 33, 201, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (64, '菜品64', '64.jpg', 24, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 50, 18, 286, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (65, '菜品65', '65.jpg', 25, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 39, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (66, '菜品66', '66.jpg', 25, '美味しい', FALSE, 0.01, 0.02, 0.01, 0.01, 90, 27, 407, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (67, '菜品67', '67.jpg', 25, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 60, 17, 316, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (68, '菜品68', '68.jpg', 25, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 44, 384, '懒得填了',
        '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (69, '菜品69', '69.jpg', 26, '美味しい', FALSE, 0.01, 0.01, NULL, NULL, NULL, NULL, 365, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (70, '菜品70', '70.jpg', 26, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 45, 168, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (71, '菜品71', '71.jpg', 26, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 50, 273, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (72, '菜品72', '72.jpg', 27, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 100, 14, 233, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (73, '菜品73', '73.jpg', 27, '美味しい', TRUE, 0.01, 0.01, NULL, NULL, NULL, NULL, 280, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES
  (74, '菜品74', '74.jpg', 27, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 70, 46, 95, '懒得填了',
   '懒得填了');
INSERT INTO food (id, name, image_url, category_id, introduction, is_on_sale, pack_fee, standard_price, member_price, member_price_cost, member_price_limit, member_price_remaining, sale_count, insert_user, update_user)
VALUES (75, '菜品75', '75.jpg', 27, '美味しい', TRUE, 0.01, 0.02, 0.01, 0.01, 80, 44, 51, '懒得填了',
        '懒得填了');

-- 菜品分类
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (1, '主食', 's_1', 's_1', 's_1');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (2, '饮料', 's_1', 's_1', 's_1');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (3, '甜品', 's_1', 's_1', 's_1');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (4, '主食', 's_2', 's_2', 's_2');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (5, '饮料', 's_2', 's_2', 's_2');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (6, '甜品', 's_2', 's_2', 's_2');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (7, '主食', 's_3', 's_3', 's_3');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (8, '饮料', 's_3', 's_3', 's_3');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (9, '甜品', 's_3', 's_3', 's_3');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (10, '主食', 's_4', 's_4', 's_4');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (11, '饮料', 's_4', 's_4', 's_4');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (12, '甜品', 's_4', 's_4', 's_4');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (13, '主食', 's_5', 's_5', 's_5');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (14, '饮料', 's_5', 's_5', 's_5');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (15, '甜品', 's_5', 's_5', 's_5');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (16, '主食', 's_6', 's_6', 's_6');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (17, '饮料', 's_6', 's_6', 's_6');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (18, '甜品', 's_6', 's_6', 's_6');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (19, '主食', 's_7', 's_7', 's_7');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (20, '饮料', 's_7', 's_7', 's_7');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (21, '甜品', 's_7', 's_7', 's_7');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (22, '主食', 's_8', 's_8', 's_8');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (23, '饮料', 's_8', 's_8', 's_8');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (24, '甜品', 's_8', 's_8', 's_8');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (25, '主食', 's_9', 's_9', 's_9');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (26, '饮料', 's_9', 's_9', 's_9');
INSERT INTO food_category (id, name, stall_id, insert_user, update_user)
VALUES (27, '甜品', 's_9', 's_9', 's_9');

-- 全局通知
INSERT INTO global_notice (id, content, insert_user, update_user)
VALUES (1, '全局通知：欢迎使用', 'o_1', 'o_1');
INSERT INTO global_notice (id, content, insert_user, update_user) VALUES (2, '第二条通知', 'o_1', 'o_1');
INSERT INTO global_notice (id, content, insert_user, update_user) VALUES (3, '第三条通知', 'o_1', 'o_1');

-- 运营方
-- 密码均为 123
INSERT INTO operator (username, salt, pwd_hash, role)
VALUES ('admin', 'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
        '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 1);
INSERT INTO operator (username, salt, pwd_hash, role, insert_user, update_user)
VALUES ('oper', 'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
        '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 2, 'o_1', 'o_1');
INSERT INTO operator (username, salt, pwd_hash, role, insert_user, update_user)
VALUES ('oper2', 'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
        '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 1, 'o_2', 'o_2');

-- 订单
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000001', 'openid_000000000000000000002', 's_4', '18614998310', 1,
                                            date_add(current_timestamp, INTERVAL 64 MINUTE),
                                            '订单1的备注', TRUE, 2,
                                            date_add(current_timestamp, INTERVAL 3 MINUTE), NULL,
        NULL, 'openid_000000000000000000002', 'openid_000000000000000000002');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000002', 'openid_000000000000000000003', 's_5', '18617206895', 2,
                                            date_add(current_timestamp, INTERVAL 22 MINUTE),
                                            '订单2的备注', TRUE, 0, NULL, NULL, NULL,
        'openid_000000000000000000003', 'openid_000000000000000000003');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000003', 'openid_000000000000000000006', 's_5', '18619410544', 3,
                                            date_add(current_timestamp, INTERVAL 45 MINUTE),
                                            '订单3的备注', TRUE, 6,
                                            date_add(current_timestamp, INTERVAL 3 MINUTE),
                                            date_add(current_timestamp, INTERVAL 6 MINUTE), NULL,
        'openid_000000000000000000006', 'openid_000000000000000000006');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000004', 'openid_000000000000000000005', 's_5', '18610730644', 4,
                                            date_add(current_timestamp, INTERVAL 59 MINUTE),
                                            '订单4的备注', FALSE, 3,
                                            date_add(current_timestamp, INTERVAL 3 MINUTE), NULL,
        NULL, 'openid_000000000000000000005', 'openid_000000000000000000005');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000005', 'openid_000000000000000000004', 's_6', '18611889393', 5,
                                            date_add(current_timestamp, INTERVAL 24 MINUTE),
                                            '订单5的备注', TRUE, 7, NULL, NULL, NULL,
        'openid_000000000000000000004', 'openid_000000000000000000004');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000006', 'openid_000000000000000000003', 's_4', '18614444542', 6,
                                            date_add(current_timestamp, INTERVAL 83 MINUTE),
                                            '订单6的备注', TRUE, 4,
                                            date_add(current_timestamp, INTERVAL 3 MINUTE),
                                            date_add(current_timestamp, INTERVAL 6 MINUTE), NULL,
        'openid_000000000000000000003', 'openid_000000000000000000003');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000007', 'openid_000000000000000000001', 's_3', '18618059728', 7,
                                            date_add(current_timestamp, INTERVAL 18 MINUTE),
                                            '订单7的备注', TRUE, 3,
                                            date_add(current_timestamp, INTERVAL 3 MINUTE), NULL,
        NULL, 'openid_000000000000000000001', 'openid_000000000000000000001');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000008', 'openid_000000000000000000006', 's_2', '18610116707', 8,
                                            date_add(current_timestamp, INTERVAL 59 MINUTE),
                                            '订单8的备注', TRUE, 5,
                                            date_add(current_timestamp, INTERVAL 3 MINUTE),
                                            date_add(current_timestamp, INTERVAL 6 MINUTE),
        date_add(current_timestamp, INTERVAL 9 MINUTE), 'openid_000000000000000000006',
        'openid_000000000000000000006');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES ('00000000000000000000000000000009', 'openid_000000000000000000003', 's_2', '18612578513', 9,
                                            date_add(current_timestamp, INTERVAL 75 MINUTE),
                                            '订单9的备注', FALSE, 1,
                                            date_add(current_timestamp, INTERVAL 3 MINUTE), NULL,
        NULL, 'openid_000000000000000000003', 'openid_000000000000000000003');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES
  ('00000000000000000000000000000010', 'openid_000000000000000000002', 's_4', '18611406423', 10,
                                       date_add(current_timestamp, INTERVAL 31 MINUTE), '订单10的备注',
                                       TRUE, 7, NULL, NULL, NULL, 'openid_000000000000000000002',
   'openid_000000000000000000002');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES
  ('00000000000000000000000000000011', 'openid_000000000000000000005', 's_6', '18616414392', 11,
                                       date_add(current_timestamp, INTERVAL 75 MINUTE), '订单11的备注',
                                       TRUE, 6, date_add(current_timestamp, INTERVAL 3 MINUTE),
                                       date_add(current_timestamp, INTERVAL 6 MINUTE),
   date_add(current_timestamp, INTERVAL 9 MINUTE), 'openid_000000000000000000005',
   'openid_000000000000000000005');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES
  ('00000000000000000000000000000012', 'openid_000000000000000000002', 's_5', '18610665186', 12,
                                       date_add(current_timestamp, INTERVAL 67 MINUTE), '订单12的备注',
                                       FALSE, 1, date_add(current_timestamp, INTERVAL 3 MINUTE),
                                       NULL, NULL, 'openid_000000000000000000002',
   'openid_000000000000000000002');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES
  ('00000000000000000000000000000013', 'openid_000000000000000000004', 's_8', '18612691341', 13,
                                       date_add(current_timestamp, INTERVAL 68 MINUTE), '订单13的备注',
                                       FALSE, 5, date_add(current_timestamp, INTERVAL 3 MINUTE),
                                       date_add(current_timestamp, INTERVAL 6 MINUTE),
   date_add(current_timestamp, INTERVAL 9 MINUTE), 'openid_000000000000000000004',
   'openid_000000000000000000004');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES
  ('00000000000000000000000000000014', 'openid_000000000000000000005', 's_8', '18613814498', 14,
                                       date_add(current_timestamp, INTERVAL 84 MINUTE), '订单14的备注',
                                       TRUE, 4, date_add(current_timestamp, INTERVAL 3 MINUTE),
                                       date_add(current_timestamp, INTERVAL 6 MINUTE), NULL,
   'openid_000000000000000000005', 'openid_000000000000000000005');
INSERT INTO `order` (id, consumer_id, stall_id, phone, appointment_id, appointment_time, remark, is_pack, status, pay_time, refund_application_time, refund_end_time, insert_user, update_user)
VALUES
  ('00000000000000000000000000000015', 'openid_000000000000000000001', 's_1', '18615483811', 15,
                                       date_add(current_timestamp, INTERVAL 71 MINUTE), '订单15的备注',
                                       TRUE, 2, date_add(current_timestamp, INTERVAL 3 MINUTE),
                                       NULL, NULL, 'openid_000000000000000000001',
   'openid_000000000000000000001');

-- 订购菜品
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (29, '00000000000000000000000000000001', 1, TRUE, 1.5, 15, 8, 9, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (34, '00000000000000000000000000000001', 3, FALSE, 0.5, 13, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (35, '00000000000000000000000000000001', 4, TRUE, 1, 16, 5, 12, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (40, '00000000000000000000000000000002', 1, FALSE, 1, 17, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (42, '00000000000000000000000000000002', 1, TRUE, 0.5, 20, 9, 12, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (45, '00000000000000000000000000000002', 4, TRUE, 1, 15, 5, 12, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (44, '00000000000000000000000000000003', 1, TRUE, 1, 16, 6, 10, 2, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (42, '00000000000000000000000000000003', 4, TRUE, 1, 15, 9, 12, -1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (37, '00000000000000000000000000000003', 4, TRUE, 1.5, 16, 7, 11, 1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (54, '00000000000000000000000000000004', 2, FALSE, 0, 19, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (57, '00000000000000000000000000000004', 1, TRUE, 0, 20, 5, 9, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (55, '00000000000000000000000000000004', 4, FALSE, 0, 13, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (48, '00000000000000000000000000000005', 3, FALSE, 1, 18, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (52, '00000000000000000000000000000005', 5, FALSE, 0.5, 18, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (50, '00000000000000000000000000000005', 5, TRUE, 1, 14, 9, 11, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (68, '00000000000000000000000000000006', 4, TRUE, 0.5, 15, 8, 10, -1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (66, '00000000000000000000000000000006', 2, TRUE, 0.5, 19, 8, 12, -1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (72, '00000000000000000000000000000006', 1, FALSE, 1.5, 16, NULL, NULL, -2, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (22, '00000000000000000000000000000007', 2, FALSE, 1.5, 15, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (17, '00000000000000000000000000000007', 3, FALSE, 1, 14, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (25, '00000000000000000000000000000007', 5, TRUE, 1.5, 14, 5, 11, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (9, '00000000000000000000000000000008', 1, TRUE, 1.5, 14, 9, 9, -2, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (13, '00000000000000000000000000000008', 5, FALSE, 0.5, 13, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (11, '00000000000000000000000000000008', 5, FALSE, 1, 19, NULL, NULL, 2, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (13, '00000000000000000000000000000009', 5, FALSE, 0, 16, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (15, '00000000000000000000000000000009', 5, TRUE, 0, 19, 7, 10, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (14, '00000000000000000000000000000009', 5, TRUE, 0, 14, 8, 11, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (29, '00000000000000000000000000000010', 5, FALSE, 1, 18, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (32, '00000000000000000000000000000010', 1, TRUE, 0.5, 15, 8, 9, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (36, '00000000000000000000000000000010', 4, TRUE, 1, 16, 9, 11, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (65, '00000000000000000000000000000011', 4, TRUE, 1.5, 19, 6, 11, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (69, '00000000000000000000000000000011', 1, TRUE, 1, 14, 5, 10, 1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (73, '00000000000000000000000000000011', 3, FALSE, 0.5, 20, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (41, '00000000000000000000000000000012', 4, FALSE, 0, 17, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (43, '00000000000000000000000000000012', 5, FALSE, 0, 17, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (38, '00000000000000000000000000000012', 1, FALSE, 0, 20, NULL, NULL, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (60, '00000000000000000000000000000013', 2, TRUE, 0, 14, 9, 11, -1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (62, '00000000000000000000000000000013', 5, TRUE, 0, 13, 9, 10, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (63, '00000000000000000000000000000013', 5, FALSE, 0, 13, NULL, NULL, 1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (62, '00000000000000000000000000000014', 4, FALSE, 1, 15, NULL, NULL, -2, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (60, '00000000000000000000000000000014', 5, FALSE, 0.5, 17, NULL, NULL, -1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (59, '00000000000000000000000000000014', 1, TRUE, 1.5, 19, 9, 11, 0, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (3, '00000000000000000000000000000015', 4, FALSE, 0.5, 13, NULL, NULL, 1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (5, '00000000000000000000000000000015', 3, TRUE, 1.5, 17, 9, 9, 1, '懒得填了', '懒得填了');
INSERT INTO ordered_food (food_id, order_id, count, is_member, pack_fee, standard_price, member_price_cost, member_price, mark, insert_user, update_user)
VALUES (7, '00000000000000000000000000000015', 3, TRUE, 1.5, 13, 6, 9, -1, '懒得填了', '懒得填了');

-- 推荐菜品
INSERT INTO recommended_food (food_id, insert_user, update_user) VALUES (23, 'o_1', 'o_1');
INSERT INTO recommended_food (food_id, insert_user, update_user) VALUES (54, 'o_1', 'o_1');
INSERT INTO recommended_food (food_id, insert_user, update_user) VALUES (12, 'o_1', 'o_1');
INSERT INTO recommended_food (food_id, insert_user, update_user) VALUES (55, 'o_1', 'o_1');
INSERT INTO recommended_food (food_id, insert_user, update_user) VALUES (45, 'o_1', 'o_1');
INSERT INTO recommended_food (food_id, insert_user, update_user) VALUES (8, 'o_1', 'o_1');

-- 商户
-- 所有密码均为 123
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (1, '黄焖鸡米饭', '13300001111', '1.jpg', '黄焖鸡米饭商户介绍', TRUE, FALSE, 'stall1',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (1, '喜多屋', '15522226666', '2.jpg', '喜多屋商户介绍', TRUE, TRUE, 'stall2',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (2, '肯德鸡', '13287654321', '3.jpg', '肯德鸡商户介绍', FALSE, TRUE, 'stall3',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (2, '金拱门', '18612349876', '4.jpg', '金拱门商户介绍', TRUE, FALSE, 'stall4',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (2, '汉堡王', '18800002222', '5.jpg', '汉堡王商户介绍', TRUE, TRUE, 'stall5',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (4, '庆丰包子', '13399997777', '6.jpg', '庆丰包子商户介绍', FALSE, FALSE, 'stall6',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (4, '全聚德', '13100226644', '7.jpg', '全聚德商户介绍', TRUE, TRUE, 'stall7',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (4, '泷千家', '13912345678', '8.jpg', '泷千家商户介绍', TRUE, FALSE, 'stall8',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
INSERT INTO stall (dining_hall_id, name, phone, image_url, introduction, is_open, auto_accept_at_rest, username, salt, pwd_hash, insert_user, update_user)
VALUES (3, '华莱士', '13398765432', '9.jpg', '华莱士商户介绍', FALSE, FALSE, 'stall9',
           'VhNdhH8lqoVRId2rVHddDljiKNTRieqT',
           '3fd65bf4b5689a3adcea05a19d9c826a98d377d005f1dee26b242bb0ab0589b1', 'o_1', 'o_1');
