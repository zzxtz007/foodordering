SET NAMES utf8;

DROP TABLE IF EXISTS banner CASCADE;
DROP TABLE IF EXISTS consumer CASCADE;
DROP TABLE IF EXISTS dining_hall CASCADE;
DROP TABLE IF EXISTS feedback CASCADE;
DROP TABLE IF EXISTS food CASCADE;
DROP TABLE IF EXISTS food_category CASCADE;
DROP TABLE IF EXISTS global_notice CASCADE;
DROP TABLE IF EXISTS operator CASCADE;
DROP TABLE IF EXISTS operator_id_seq CASCADE;
DROP TABLE IF EXISTS `order` CASCADE;
DROP TABLE IF EXISTS ordered_food CASCADE;
DROP TABLE IF EXISTS recommended_food CASCADE;
DROP TABLE IF EXISTS stall CASCADE;
DROP TABLE IF EXISTS consignee_information CASCADE;
DROP TABLE IF EXISTS stall_id_seq CASCADE;

CREATE TABLE banner
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  image_url VARCHAR(100) NOT NULL
  COMMENT '图片 URL',
  link VARCHAR(2000) NOT NULL
  COMMENT '广告 URL',
  weight INT NOT NULL DEFAULT -1
  COMMENT '排序权重，权重大者靠前，小者靠后',
  insert_user VARCHAR(12) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(12) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '横幅广告';

CREATE TABLE consumer
(
  id VARCHAR(28) PRIMARY KEY
  COMMENT '用户 ID（微信 Open ID）',
  is_member BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '会员标记
 TRUE=会员
 FALSE=普通用户',
  phone VARCHAR(20) COMMENT '手机号码,默认为空,用户获取后填入数据库',
  name VARCHAR(64) COMMENT '姓名，普通用户该字段为 NULL',
  student_id VARCHAR(64) COMMENT '学生证号，普通用户该字段为 NULL',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '用户';

CREATE TABLE dining_hall
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  name VARCHAR(32) NOT NULL
  COMMENT '食堂名称',
  image_url VARCHAR(100) NOT NULL
  COMMENT '图片 URL',
  start_time TIME NOT NULL
  COMMENT '开始营业时间',
  end_time TIME NOT NULL
  COMMENT '结束营业时间',
  introduction VARCHAR(100) DEFAULT ''
  COMMENT '介绍',
  weight INT NOT NULL DEFAULT -1
  COMMENT '排序权重，权重大者靠前，小者靠后',
  insert_user VARCHAR(12) COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(12) COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '食堂';

CREATE TABLE feedback
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  content VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '反馈内容',
  insert_user VARCHAR(28) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(28) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '用户反馈';

CREATE TABLE food
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  name VARCHAR(32) NOT NULL
  COMMENT '菜品名称',
  image_url VARCHAR(100) NOT NULL
  COMMENT '图片 URL',
  category_id INT NOT NULL
  COMMENT '菜品分类 ID',
  introduction VARCHAR(100) DEFAULT ''
  COMMENT '介绍',
  is_on_sale BIT(1) NOT NULL DEFAULT TRUE
  COMMENT '在售标记
 TRUE=在售
 FALSE=下架',
  pack_fee NUMERIC(12, 8) NOT NULL DEFAULT 0
  COMMENT '打包费用',
  standard_price NUMERIC(14, 8) NOT NULL
  COMMENT '标准价格，即非会员价格',
  member_price NUMERIC(14, 8) COMMENT '会员价格',
  member_price_cost NUMERIC(14, 8) COMMENT '会员价格成本，即商户提供给运营的会员价菜品的价格',
  member_price_limit INT COMMENT '会员价菜品每日限量',
  member_price_remaining INT COMMENT '当日剩余会员价菜品数量',
  sale_count INT NOT NULL
  COMMENT '总销量',
  weight INT NOT NULL DEFAULT -1
  COMMENT '排序权重，权重大者靠前，小者靠后',
  insert_user VARCHAR(28) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(28) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '菜品';

CREATE TABLE food_category
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  name VARCHAR(32) NOT NULL
  COMMENT '分类名称',
  stall_id VARCHAR(12) NOT NULL
  COMMENT '商户 ID',
  weight INT NOT NULL DEFAULT -1
  COMMENT '排序权重，权重大者靠前，小者靠后',
  insert_user VARCHAR(12) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(12) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '菜品分类';

CREATE TABLE global_notice
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  content VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '通知内容',
  insert_user VARCHAR(12) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '',
  update_user VARCHAR(12) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '全局通知';

CREATE TABLE operator
(
  id VARCHAR(12) PRIMARY KEY
  COMMENT '自增 ID，由“o_”开头',
  username VARCHAR(32) NOT NULL UNIQUE
  COMMENT '用户名',
  salt VARCHAR(64) NOT NULL
  COMMENT '盐值',
  pwd_hash VARCHAR(64) NOT NULL
  COMMENT '加盐密码的散列值',
  role INT NOT NULL DEFAULT 2
  COMMENT '运营用户角色
 1=超级管理员（拥有查看报表的权限）
 2=普通管理员',
  insert_user VARCHAR(12) COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(12) COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '运营用户';

CREATE TABLE operator_id_seq
(
  id INT PRIMARY KEY AUTO_INCREMENT
);

DELIMITER //
CREATE TRIGGER operator_id_trig
  BEFORE INSERT
  ON operator
  FOR EACH ROW
  BEGIN
    IF new.id IS NULL
    THEN
      INSERT INTO operator_id_seq VALUES ();
      SET new.id = concat('o_', last_insert_id());
    END IF;
  END;
//
DELIMITER ;

CREATE TABLE `order`
(
  id VARCHAR(32) PRIMARY KEY
  COMMENT '自增 ID',
  consumer_id VARCHAR(28) NOT NULL
  COMMENT '用户 ID',
  stall_id VARCHAR(12) NOT NULL
  COMMENT '商户 ID',
  phone VARCHAR(18) NOT NULL
  COMMENT '用户联系电话',
  appointment_id INT NOT NULL
  COMMENT '取餐号',
  appointment_time DATETIME
  COMMENT '取餐时间,当时间为null时表示菜品为立即制作',
  remark VARCHAR(100) NOT NULL
  COMMENT '订单备注',
  is_pack BIT(1) NOT NULL
  COMMENT '打包标记
 TRUE=打包
 FALSE=不打包',
  status INT NOT NULL
  COMMENT '订单状态
 0=未支付
 1=未接单
 2=已接单，即已完成
 3=商家拒单
 4=申请退款
 5=已退款
 6=商家拒绝退款
 7=已取消',
  order_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '下单时间',
  pay_time DATETIME COMMENT '付款成功时间，未支付时为 NULL',
  refund_application_time DATETIME COMMENT '退款申请时间',
  refund_end_time DATETIME COMMENT '退款结束（退款完成和退款被拒绝）时间',
  insert_user VARCHAR(28) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(28) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '订单';

CREATE TABLE ordered_food
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  food_id INT NOT NULL
  COMMENT '菜品 ID',
  order_id VARCHAR(32) NOT NULL
  COMMENT '订单 ID',
  count TINYINT NOT NULL
  COMMENT '购买数量',
  is_member BIT(1) NOT NULL
  COMMENT '会员价格标记
 TRUE=以会员价格购买
 FALSE=以标准价格购买',
  pack_fee NUMERIC(12, 8) NOT NULL
  COMMENT '购买时的打包费用',
  standard_price NUMERIC(14, 8) COMMENT '购买时的标准价格，以会员价格购买时该字段为 NULL',
  member_price_cost NUMERIC(14, 8) COMMENT '购买时的会员价成本，以标准价格购买时该字段为 NULL',
  member_price NUMERIC(14, 8) COMMENT '购买时的会员价格，以标准价格购买时该字段为 NULL',
  mark TINYINT DEFAULT 0
  COMMENT '评分,默认为中间值0',
  insert_user VARCHAR(28) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(28) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '订单菜品';

CREATE TABLE recommended_food
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增 ID',
  food_id INT NOT NULL
  COMMENT '菜品 ID',
  weight INT NOT NULL DEFAULT -1
  COMMENT '排序权重，权重大者靠前，小者靠后',
  insert_user VARCHAR(12) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(12) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '推荐菜品';

CREATE TABLE stall
(
  id VARCHAR(12) PRIMARY KEY
  COMMENT '自增 ID，由“s_”开头',
  dining_hall_id INT NOT NULL
  COMMENT '食堂 ID',
  role TINYINT NOT NULL DEFAULT 1
  COMMENT '商户角色
 1=食堂档口
 2=早点
 3=夜间超市',
  name VARCHAR(32) NOT NULL
  COMMENT '商户名称',
  phone VARCHAR(18) COMMENT '联系电话',
  image_url VARCHAR(100) NOT NULL
  COMMENT '图片 URL',
  introduction VARCHAR(100) DEFAULT ''
  COMMENT '介绍',
  is_open BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '营业标记
 TRUE=正在营业
 FALSE=停止营业',
  auto_accept_at_rest BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '商家休息时自动接单标记
 TRUE=商家休息时自动接单
 FALSE=商家休息时不自动接单',
  weight INT NOT NULL DEFAULT -1
  COMMENT '排序权重，权重大者靠前，小者靠后',
  username VARCHAR(32) NOT NULL UNIQUE
  COMMENT '用户名',
  salt VARCHAR(64) NOT NULL
  COMMENT '盐值',
  pwd_hash VARCHAR(64) NOT NULL
  COMMENT '加盐密码的散列值',
  insert_user VARCHAR(12) NOT NULL
  COMMENT '插入数据的用户',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_user VARCHAR(12) NOT NULL
  COMMENT '最后修改数据的用户',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '商户';

CREATE TABLE consignee_information
(
  id INT PRIMARY KEY AUTO_INCREMENT
  COMMENT '自增序列',
  consumer_id VARCHAR(28) NOT NULL
  COMMENT '客户 id',
  address VARCHAR(50) NOT NULL
  COMMENT '地址',
  empty1 VARCHAR(100) COMMENT '预留字段1',
  empty2 VARCHAR(100) COMMENT '预留字段2',
  insert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入数据的时间',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '最后修改数据的时间',
  is_deleted BIT(1) NOT NULL DEFAULT FALSE
  COMMENT '删除标记'
)
  COMMENT '收货信息表';

CREATE TABLE stall_id_seq
(
  id INT PRIMARY KEY AUTO_INCREMENT
);

DELIMITER //
CREATE TRIGGER stall_id_trig
  BEFORE INSERT
  ON stall
  FOR EACH ROW
  BEGIN
    IF new.id IS NULL
    THEN
      INSERT INTO stall_id_seq VALUES ();
      SET new.id = concat('s_', last_insert_id());
    END IF;
  END;
//
DELIMITER ;
