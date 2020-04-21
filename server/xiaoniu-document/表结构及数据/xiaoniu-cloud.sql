/*
 Navicat Premium Data Transfer

 Source Server         : 私服
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : 39.104.186.167:3506
 Source Schema         : xiaoniu-cloud

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 21/04/2020 16:17:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_base
-- ----------------------------
DROP TABLE IF EXISTS `t_base`;
CREATE TABLE `t_base`  (
  `id` bigint(18) NOT NULL COMMENT 'ID主键',
  `del` int(1) NOT NULL COMMENT '是否删除 0:未删除 1:已删除',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间(13位时间戳)',
  `update_time` bigint(13) NULL DEFAULT NULL COMMENT '更新时间(13位时间戳)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(18) NOT NULL COMMENT 'ID主键',
  `del` int(1) NOT NULL COMMENT '是否删除 0:未删除 1:已删除',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间(13位时间戳)',
  `update_time` bigint(13) NULL DEFAULT NULL COMMENT '更新时间(13位时间戳)',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
