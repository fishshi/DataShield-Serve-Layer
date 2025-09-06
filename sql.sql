DROP DATABASE IF EXISTS `db_DataShield`;

CREATE DATABASE `db_DataShield`;

USE `db_DataShield`;

DROP TABLE IF EXISTS `tb_user_auth`;

CREATE TABLE `tb_user_auth` (
    `id` BIGINT PRIMARY KEY,
    `username` VARCHAR(32) UNIQUE NOT NULL,
    `password` VARCHAR(64) NOT NULL
);

DROP TABLE IF EXISTS `tb_user_info`;

CREATE TABLE `tb_user_info` (
    `id` BIGINT PRIMARY KEY,
    `username` VARCHAR(32) UNIQUE NOT NULL,
    `avatar_url` VARCHAR(256),
    `email` VARCHAR(64),
    `phone` VARCHAR(16)
);

DROP TABLE IF EXISTS `tb_user_remote_database`;

CREATE TABLE `tb_user_remote_database` (
    `id` BIGINT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `db_host` VARCHAR(64) NOT NULL,
    `db_port` INT NOT NULL,
    `db_type` INT NOT NULL,
    `db_name` VARCHAR(64) NOT NULL,
    `db_username` VARCHAR(64) NOT NULL,
    `db_password` VARCHAR(64) NOT NULL
);

DROP TABLE IF EXISTS `tb_identify`;

CREATE TABLE `tb_identify` (
    `id` BIGINT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `identity_name` VARCHAR(64) NOT NULL,
    `is_remote` INT NOT NULL,
    `db_name` VARCHAR(64) NOT NULL,
    `table_name` VARCHAR(64) NOT NULL,
    `colomns` VARCHAR(256) NOT NULL,
    `status` INT NOT NULL DEFAULT 0,
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS `tb_task`;

CREATE TABLE `tb_task` (
    `id` BIGINT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `is_remote` INT NOT NULL,
    `task_name` VARCHAR(64),
    `db_name` VARCHAR(64) NOT NULL,
    `db_table` VARCHAR(64) NOT NULL,
    `db_columns` VARCHAR(256) NOT NULL,
    `mask_rule` INT NOT NULL DEFAULT 0,
    `target_table` VARCHAR(64) NOT NULL,
    `status` INT NOT NULL DEFAULT 0,
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);