/*
 Navicat Premium Data Transfer

 Source Server         : rootSQL
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : springboot-vue

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 19/12/2021 17:36:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `local_date_time` datetime NOT NULL COMMENT '日志时间',
  `user_id` int NOT NULL COMMENT '客户id',
  `storage_id` int NOT NULL COMMENT '商品id',
  `action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `log_f_userid`(`user_id`) USING BTREE,
  INDEX `log_f_storageid`(`storage_id`) USING BTREE,
  CONSTRAINT `log_f_storageid` FOREIGN KEY (`storage_id`) REFERENCES `storage` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `log_f_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `store_id` int NOT NULL,
  `storage_id` int NOT NULL,
  `num` int NOT NULL,
  `ostate` int NOT NULL COMMENT '发货状态：0没发货，1发货，2确认收货',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `custom_id`(`customer_id`) USING BTREE,
  INDEX `order_storage_id`(`storage_id`) USING BTREE,
  INDEX `order_store_id`(`store_id`) USING BTREE,
  CONSTRAINT `custom_id` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `order_storage_id` FOREIGN KEY (`storage_id`) REFERENCES `storage` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_store_id` FOREIGN KEY (`store_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `store_id` int NOT NULL COMMENT '店家id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '品牌',
  `create_time` datetime NULL DEFAULT NULL COMMENT '出厂日期',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '封面地址',
  `state` int NULL DEFAULT NULL COMMENT '是否上架，0否，1是',
  `sales_volume` int NULL DEFAULT 0 COMMENT '销量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `store_id`(`store_id`) USING BTREE,
  CONSTRAINT `store_id` FOREIGN KEY (`store_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '\r\n用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL,
  `account` decimal(10, 2) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role`(`role`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_storage
-- ----------------------------
DROP TABLE IF EXISTS `user_storage`;
CREATE TABLE `user_storage`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户',
  `storage_id` int NOT NULL COMMENT '商品',
  `num` int NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`user_id`, `storage_id`) USING BTREE,
  INDEX `storage_id`(`storage_id`) USING BTREE,
  CONSTRAINT `storage_id` FOREIGN KEY (`storage_id`) REFERENCES `storage` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
