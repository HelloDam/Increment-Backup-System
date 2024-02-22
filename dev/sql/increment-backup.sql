/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库8.0
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3308
 Source Schema         : increment-backup

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 22/02/2024 20:50:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for backup_file
-- ----------------------------
DROP TABLE IF EXISTS `backup_file`;
CREATE TABLE `backup_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1760118786624602114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_0
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_0`;
CREATE TABLE `backup_file_0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_1
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_1`;
CREATE TABLE `backup_file_1` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_10
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_10`;
CREATE TABLE `backup_file_10` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_11
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_11`;
CREATE TABLE `backup_file_11` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_12
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_12`;
CREATE TABLE `backup_file_12` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_13
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_13`;
CREATE TABLE `backup_file_13` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_14
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_14`;
CREATE TABLE `backup_file_14` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_15
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_15`;
CREATE TABLE `backup_file_15` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_2
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_2`;
CREATE TABLE `backup_file_2` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7357683524236738571 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_3
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_3`;
CREATE TABLE `backup_file_3` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_4
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_4`;
CREATE TABLE `backup_file_4` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_5
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_5`;
CREATE TABLE `backup_file_5` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_6
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_6`;
CREATE TABLE `backup_file_6` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1760576231053660165 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_7
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_7`;
CREATE TABLE `backup_file_7` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_8
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_8`;
CREATE TABLE `backup_file_8` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1760168204228939782 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_9
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_9`;
CREATE TABLE `backup_file_9` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_target_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `source_file_path` varchar(1024) DEFAULT NULL COMMENT '数据源根目录',
  `target_file_path` varchar(1024) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `last_backup_time` datetime DEFAULT NULL COMMENT '最后一次备份时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_file_bf` (`backup_source_id`,`father_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history`;
CREATE TABLE `backup_file_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1760118786742042626 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_0
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_0`;
CREATE TABLE `backup_file_history_0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_1
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_1`;
CREATE TABLE `backup_file_history_1` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_10
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_10`;
CREATE TABLE `backup_file_history_10` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_11
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_11`;
CREATE TABLE `backup_file_history_11` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_12
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_12`;
CREATE TABLE `backup_file_history_12` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_13
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_13`;
CREATE TABLE `backup_file_history_13` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_14
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_14`;
CREATE TABLE `backup_file_history_14` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_15
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_15`;
CREATE TABLE `backup_file_history_15` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_2
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_2`;
CREATE TABLE `backup_file_history_2` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7357683524267147276 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_3
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_3`;
CREATE TABLE `backup_file_history_3` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_4
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_4`;
CREATE TABLE `backup_file_history_4` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_5
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_5`;
CREATE TABLE `backup_file_history_5` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_6
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_6`;
CREATE TABLE `backup_file_history_6` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1760576231166906388 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_7
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_7`;
CREATE TABLE `backup_file_history_7` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_8
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_8`;
CREATE TABLE `backup_file_history_8` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1760168204845502484 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_file_history_9
-- ----------------------------
DROP TABLE IF EXISTS `backup_file_history_9`;
CREATE TABLE `backup_file_history_9` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_file_id` bigint DEFAULT NULL COMMENT '备份文件id',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `backup_source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `modify_time` bigint DEFAULT NULL COMMENT '文件修改时间',
  `file_size` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `backup_start_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `backup_end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `backup_target_file_path` varchar(4096) DEFAULT NULL COMMENT '文件备份后所在路径',
  `backup_target_root_id` bigint DEFAULT NULL COMMENT '备份目标目录id',
  `md5` char(64) DEFAULT NULL COMMENT '文件输入流对应的MD5，用来判断文件是否修改',
  `backup_task_id` bigint DEFAULT NULL COMMENT '所属备份任务id',
  PRIMARY KEY (`id`),
  KEY `idx_history_b` (`backup_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_source
-- ----------------------------
DROP TABLE IF EXISTS `backup_source`;
CREATE TABLE `backup_source` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `root_path` varchar(4096) NOT NULL COMMENT '数据源根目录',
  `backup_name` varchar(200) DEFAULT NULL COMMENT '备份名称，可以写一些简介来标识',
  `backup_type` tinyint DEFAULT NULL COMMENT '备份类型 0：数据源的数据全部备份到多个目标目录中 1：数据源的数据分散备份到多个目标目录中',
  `backup_num` int DEFAULT NULL COMMENT '备份次数',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1760576057149427715 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_target
-- ----------------------------
DROP TABLE IF EXISTS `backup_target`;
CREATE TABLE `backup_target` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `target_root_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7357683500069158913 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for backup_task
-- ----------------------------
DROP TABLE IF EXISTS `backup_task`;
CREATE TABLE `backup_task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `backup_source_root` varchar(4096) DEFAULT NULL COMMENT '数据源根目录',
  `backup_target_root` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `backup_status` int DEFAULT NULL COMMENT '状态 0：刚创建 1：正在执行备份 2：备份完成 3：备份失败',
  `total_file_num` int DEFAULT NULL COMMENT '总文件数量',
  `finish_file_num` int DEFAULT NULL COMMENT '已备份文件数量',
  `total_byte_num` bigint DEFAULT NULL COMMENT '总文件大小',
  `finish_byte_num` bigint DEFAULT NULL COMMENT '已备份文件大小',
  `end_time` datetime DEFAULT NULL COMMENT '备份结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '备份开始时间',
  `update_time` datetime DEFAULT NULL,
  `backup_time` bigint DEFAULT NULL COMMENT '备份时间(ms)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7357684171810013185 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_0
-- ----------------------------
DROP TABLE IF EXISTS `file_message_0`;
CREATE TABLE `file_message_0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564175811563522 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_1
-- ----------------------------
DROP TABLE IF EXISTS `file_message_1`;
CREATE TABLE `file_message_1` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759563384535162883 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_10
-- ----------------------------
DROP TABLE IF EXISTS `file_message_10`;
CREATE TABLE `file_message_10` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564175752843267 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_11
-- ----------------------------
DROP TABLE IF EXISTS `file_message_11`;
CREATE TABLE `file_message_11` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759563334836854786 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_12
-- ----------------------------
DROP TABLE IF EXISTS `file_message_12`;
CREATE TABLE `file_message_12` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564157406957570 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_13
-- ----------------------------
DROP TABLE IF EXISTS `file_message_13`;
CREATE TABLE `file_message_13` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759494938631897090 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_14
-- ----------------------------
DROP TABLE IF EXISTS `file_message_14`;
CREATE TABLE `file_message_14` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759563386514874370 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_15
-- ----------------------------
DROP TABLE IF EXISTS `file_message_15`;
CREATE TABLE `file_message_15` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759556996459188227 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_2
-- ----------------------------
DROP TABLE IF EXISTS `file_message_2`;
CREATE TABLE `file_message_2` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759563385642459138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_3
-- ----------------------------
DROP TABLE IF EXISTS `file_message_3`;
CREATE TABLE `file_message_3` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759494958248656898 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_4
-- ----------------------------
DROP TABLE IF EXISTS `file_message_4`;
CREATE TABLE `file_message_4` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759563332962000899 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_5
-- ----------------------------
DROP TABLE IF EXISTS `file_message_5`;
CREATE TABLE `file_message_5` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564172015718402 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_6
-- ----------------------------
DROP TABLE IF EXISTS `file_message_6`;
CREATE TABLE `file_message_6` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564177829023746 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_7
-- ----------------------------
DROP TABLE IF EXISTS `file_message_7`;
CREATE TABLE `file_message_7` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564168106627075 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_8
-- ----------------------------
DROP TABLE IF EXISTS `file_message_8`;
CREATE TABLE `file_message_8` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564173341118466 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for file_message_9
-- ----------------------------
DROP TABLE IF EXISTS `file_message_9`;
CREATE TABLE `file_message_9` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `backup_source_id` bigint DEFAULT NULL COMMENT '数据源id',
  `source_file_path` varchar(4096) DEFAULT NULL COMMENT '源文件路径',
  `target_file_path` varchar(4096) DEFAULT NULL COMMENT '备份目标目录根目录',
  `file_suffix` varchar(30) DEFAULT NULL COMMENT '文件后缀',
  `file_length` bigint DEFAULT NULL COMMENT '文件大小 byte',
  `file_length_after_compress` bigint DEFAULT NULL COMMENT '压缩后文件大小 byte',
  `father_id` bigint DEFAULT NULL COMMENT '父文件夹id',
  `is_compress` tinyint DEFAULT NULL COMMENT '是否压缩',
  `file_type` tinyint DEFAULT NULL COMMENT '文件类型 0：目录 1：文件',
  `is_contain_file` tinyint DEFAULT NULL COMMENT '是否包含子文件 0：不包含 1：包含',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1759564163719385090 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '参数名称',
  `value` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `description` varchar(1024) DEFAULT NULL COMMENT '参数描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1759214952452796419 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
