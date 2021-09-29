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

 Date: 06/01/2021 08:59:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES (1, 'superManager', '2020-11-28 22:32:59');
INSERT INTO `t_sys_role` VALUES (2, '草', '2020-02-20 10:53:47');
INSERT INTO `t_sys_role` VALUES (3, '火', '2020-03-10 12:30:15');
INSERT INTO `t_sys_role` VALUES (4, '水', '2017-04-05 17:01:52');

SET FOREIGN_KEY_CHECKS = 1;
