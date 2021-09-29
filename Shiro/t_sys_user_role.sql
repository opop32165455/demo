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

 Date: 06/01/2021 09:01:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `uid` int(10) NOT NULL,
  `rid` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES (1, 1, 1);
INSERT INTO `t_sys_user_role` VALUES (2, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (3, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (4, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (5, 2, 2);
INSERT INTO `t_sys_user_role` VALUES (6, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (7, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (8, 3, 3);
INSERT INTO `t_sys_user_role` VALUES (9, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (10, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (11, 4, 4);
INSERT INTO `t_sys_user_role` VALUES (12, 0, 0);
INSERT INTO `t_sys_user_role` VALUES (13, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
