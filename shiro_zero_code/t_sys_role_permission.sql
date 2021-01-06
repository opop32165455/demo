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

 Date: 06/01/2021 08:59:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `rid` int(10) NOT NULL,
  `pid` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_role_permission
-- ----------------------------
INSERT INTO `t_sys_role_permission` VALUES (1, 1, 1);
INSERT INTO `t_sys_role_permission` VALUES (2, 1, 2);
INSERT INTO `t_sys_role_permission` VALUES (3, 1, 3);
INSERT INTO `t_sys_role_permission` VALUES (4, 1, 4);
INSERT INTO `t_sys_role_permission` VALUES (5, 2, 2);
INSERT INTO `t_sys_role_permission` VALUES (6, 3, 3);
INSERT INTO `t_sys_role_permission` VALUES (7, 4, 4);
INSERT INTO `t_sys_role_permission` VALUES (8, 2, 1);
INSERT INTO `t_sys_role_permission` VALUES (9, 3, 1);
INSERT INTO `t_sys_role_permission` VALUES (10, 4, 1);

SET FOREIGN_KEY_CHECKS = 1;
