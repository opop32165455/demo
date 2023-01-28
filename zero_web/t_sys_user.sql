/*
 Navicat Premium Data Transfer

 Source Server         : Windows_mysql
 Source Server Type    : MySQL
 Source Server Version : 50629
 Source Host           : localhost:3306
 Source Schema         : windows_test

 Target Server Type    : MySQL
 Target Server Version : 50629
 File Encoding         : 65001

 Date: 12/12/2020 14:41:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `country` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` int(10) DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `create_uid` int(10) DEFAULT NULL,
  `last_login_time` datetime(0) DEFAULT NULL,
  `last_login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  `time_zone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `lang` int(4) NOT NULL DEFAULT 1,
  `is_sys` tinyint(4) DEFAULT 0,
  `is_open` tinyint(1) NOT NULL DEFAULT 1,
  `valid_time` datetime(0) DEFAULT NULL,
  `is_ukey` tinyint(1) DEFAULT 0,
  `ukey_id` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `limit_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES (1, 'admin', 'abc', 'CN', 'abc', 1, '2019-09-23 11:39:47', NULL, '2020-07-06 18:39:52', '10.11.111.103', NULL, 'GMT+0800', 1, 1, 0, '2037-12-31 23:59:59', 0, '', '');
INSERT INTO `t_sys_user` VALUES (31, 'root', 'root', NULL, 'b4b8daf4b8ea9d39568719e1e320076f', NULL, '2020-12-05 20:15:46', NULL, '2020-12-06 14:28:21', '0:0:0:0:0:0:0:1', NULL, NULL, 1, 0, 1, NULL, 0, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
