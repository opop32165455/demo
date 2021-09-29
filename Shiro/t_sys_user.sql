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

 Date: 06/01/2021 09:00:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录使用的用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` int(10) DEFAULT NULL COMMENT '用户组id 可以给用户分组',
  `create_time` datetime(0) NOT NULL,
  `last_login_time` datetime(0) DEFAULT NULL COMMENT '最后一次登录时间 可以用来判断是否发放优惠券',
  `last_login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最后一次登录的id 可以判断异地登录',
  `status` tinyint(2) DEFAULT NULL COMMENT '判断用户账号是否正常',
  `language` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户语言',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES (1, 'root', 'abc', 'abc', 1, '2019-09-23 11:39:47', '2020-07-06 18:39:52', '10.11.111.103', 1, '1');
INSERT INTO `t_sys_user` VALUES (2, '圆企鹅', 'yuanqie@xx.com', 'b4b8daf4b8ea9d39568719e1e320076f', NULL, '2020-12-05 20:15:46', '2020-12-06 14:28:21', '0:0:0:0:0:0:0:1', 1, '1');
INSERT INTO `t_sys_user` VALUES (3, '妙蛙种子', 'miaowazhongzi@xx.com', '', NULL, '2020-12-23 15:57:59', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (4, '火球鼠', 'huoqiushu@xx.com', '', NULL, '2020-12-17 15:57:56', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (5, 'test_user55', '33@qq.com', '', NULL, '2020-12-09 15:58:03', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (6, 'test_user66', '33@qq.com', '', NULL, '2020-12-15 15:58:06', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (7, 'test_user77', '33@qq.com', '', NULL, '2020-12-02 15:58:09', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (8, 'test_user88', '33@qq.com', '', NULL, '2020-12-02 15:58:14', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (9, 'test_user99', '33@qq.com', '', NULL, '2020-12-16 15:58:11', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (10, 'test_user100', '33@qq.com', '', NULL, '2020-12-02 15:58:17', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (11, 'test_user110', '33@qq.com', '', NULL, '2020-12-02 15:58:22', NULL, NULL, 1, '1');
INSERT INTO `t_sys_user` VALUES (12, 'test_user120', '33@qq.com', '', NULL, '2020-12-11 15:58:25', NULL, NULL, 1, '1');

SET FOREIGN_KEY_CHECKS = 1;
