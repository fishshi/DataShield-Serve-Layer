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

DROP TABLE IF EXISTS `tb_task`;

CREATE TABLE `tb_task` (`id` BIGINT PRIMARY KEY);