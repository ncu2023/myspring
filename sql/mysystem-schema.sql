CREATE TABLE `account` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(32) COMMENT '使用者名稱',
  `code` varchar(64) COMMENT '使用者密碼',
  `email` varchar(64) COMMENT '使用者信箱',
  `phone` varchar(32) COMMENT '使用者電話'
);

CREATE TABLE `product` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `photo_url` varchar(512),
  `title` varchar(32),
  `description` varchar(256),
  `price` integer,
  `store_url` varchar(512),
  `store_name` varchar(32)
);
