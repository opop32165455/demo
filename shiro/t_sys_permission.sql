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

 Date: 06/01/2021 08:59:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '该权限允许访问的url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_permission
-- ----------------------------
INSERT INTO `t_sys_permission` VALUES (1, '\r\nbrowse', '/app/list/**');
INSERT INTO `t_sys_permission` VALUES (2, 'insert', '/app/add/**');
INSERT INTO `t_sys_permission` VALUES (3, 'edit', '/app/update/**');
INSERT INTO `t_sys_permission` VALUES (4, 'delete', '/app/delete/**');

SET FOREIGN_KEY_CHECKS = 1;
