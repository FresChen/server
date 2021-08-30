/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : sql_security

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 31/08/2021 00:41:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for loophole
-- ----------------------------
DROP TABLE IF EXISTS `loophole`;
CREATE TABLE `loophole`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `User` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `solveCont` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UserID` bigint(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `UserID`(`UserID`) USING BTREE,
  INDEX `User`(`User`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of loophole
-- ----------------------------
INSERT INTO `loophole` VALUES (3, '127.0.0.7', '8080', '1121', '弱口令漏洞', '2021-08-29 22:51:55', '已处理', '张三', '修复漏洞', 3);
INSERT INTO `loophole` VALUES (5, '127.0.0.5', '11211', '11211', 'XSS跨站脚本', '2021-08-30 13:14:33', '已处理', '张四', '修复漏洞', 4);
INSERT INTO `loophole` VALUES (6, '127.0.0.9', '1010', '1131', '弱口令漏洞', '2021-08-30 13:14:35', '处理中', '张三', '', 3);
INSERT INTO `loophole` VALUES (10, '127.0.0.9', '1212', '1141', '弱口令漏洞', '2021-08-30 13:14:38', '处理中', '张三', '', 3);
INSERT INTO `loophole` VALUES (17, '127.0.0.4', '1515', '1515', 'sql注入', '2021-08-30 13:29:27', '未处理', '张三', NULL, 3);
INSERT INTO `loophole` VALUES (18, '127.0.0.5', '1161', '1161', 'XSS跨站脚本', '2021-08-30 13:38:54', '未处理', '张三', NULL, 3);

-- ----------------------------
-- Table structure for server
-- ----------------------------
DROP TABLE IF EXISTS `server`;
CREATE TABLE `server`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of server
-- ----------------------------
INSERT INTO `server` VALUES (6, '王五', '127.0.0.4', '0', '1121', '2021-10-10 00:00:00', '1121');
INSERT INTO `server` VALUES (7, '张三', '127.0.0.5', '0', '1131', '2021-10-10 00:00:00', '1131');
INSERT INTO `server` VALUES (8, '张三', '127.0.0.6', '1', '1161', '2021-10-10 00:00:00', '1161');
INSERT INTO `server` VALUES (9, '张三', '127.0.0.7', '1', '1181', '2021-10-10 00:00:00', '1181');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, '王五', '1131', '1141', '男', '2021-08-27 20:56:45', '1131');
INSERT INTO `user` VALUES (4, '张三', '1121', '1121', '男', '2021-08-27 22:43:34', '11221');

SET FOREIGN_KEY_CHECKS = 1;
