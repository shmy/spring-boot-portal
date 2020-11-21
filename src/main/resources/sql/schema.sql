DROP DATABASE IF EXISTS `portal`;
CREATE DATABASE `portal` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci;
USE `portal`;


CREATE TABLE `user`
(
    `id`         BIGINT UNSIGNED AUTO_INCREMENT,
    `username`   VARCHAR(32) UNIQUE NOT NULL COMMENT '用户名',
    `password`   VARCHAR(32) NOT NULL COMMENT '密码',
    `avatar`     VARCHAR(32) NOT NULL COMMENT '头像',
    `phone`      VARCHAR(32) NOT NULL COMMENT '手机号码',
    `enabled`    TINYINT  DEFAULT 1 COMMENT '用户是否被启用: 0 否 1 是',
    `created_at` DATETIME    NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='人员表';

CREATE TABLE `role`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT,
    `name`        VARCHAR(32)  NOT NULL COMMENT '名称',
    `description` VARCHAR(128) NOT NULL COMMENT '描述',
    `created_at`  DATETIME     NOT NULL,
    `updated_at`  DATETIME DEFAULT NULL,
    `deleted_at`  DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='角色表';

CREATE TABLE `permission`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT,
    `name`        VARCHAR(32)  NOT NULL COMMENT '名称',
    `description` VARCHAR(128) NOT NULL COMMENT '描述',
    `code`        VARCHAR(32)  NOT NULL COMMENT '对应的接口匹配代码',
    `created_at`  DATETIME     NOT NULL,
    `updated_at`  DATETIME DEFAULT NULL,
    `deleted_at`  DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='权限表';

CREATE TABLE `token`
(
    `id`         BIGINT UNSIGNED AUTO_INCREMENT,
    `type`       ENUM ('WEB', 'ANDROID', 'IOS') NOT NULL COMMENT '令牌类型',
    `token`      VARCHAR(255) COMMENT '对应token',
    `people_id`  BIGINT UNSIGNED COMMENT '人员ID',
    `created_at` DATETIME                       NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='令牌表';

# CREATE TABLE `socket`
# (
#     `id`         BIGINT UNSIGNED AUTO_INCREMENT,
#     `type`       ENUM ('WEB', 'ANDROID', 'IOS') NOT NULL COMMENT '令牌类型',
#     `socket_id`  VARCHAR(255) COMMENT 'socketId',
#     `people_id`  BIGINT UNSIGNED COMMENT '人员ID',
#     `created_at` DATETIME                       NOT NULL,
#     `updated_at` DATETIME DEFAULT NULL,
#     `deleted_at` DATETIME DEFAULT NULL,
#     PRIMARY KEY (`id`)
# ) ENGINE = INNODB COMMENT ='Socket表';

CREATE TABLE `associated_role_permission`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT,
    `role_id`       BIGINT UNSIGNED COMMENT '角色ID',
    `permission_id` BIGINT UNSIGNED COMMENT '权限ID',
    `created_at`    DATETIME NOT NULL,
    `updated_at`    DATETIME DEFAULT NULL,
    `deleted_at`    DATETIME DEFAULT NULL,
    # 关联外键
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='角色权限中间表';

CREATE TABLE `associated_role_user`
(
    `id`         BIGINT UNSIGNED AUTO_INCREMENT,
    `role_id`    BIGINT UNSIGNED COMMENT '角色ID',
    `user_id`    BIGINT UNSIGNED COMMENT '人员ID',
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME DEFAULT NULL,
    `deleted_at` DATETIME DEFAULT NULL,
    # 关联外键
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='角色人员中间表';

# 插入人员数据
INSERT INTO `user`
VALUES (1, 'admin', '123456', '/avatar.png', '13888888888', 1, NOW(), NOW(), NULL),
       (2, 'guanliyuan', '123456', '/avatar.png', '13888888888', 1, NOW(), NOW(), NULL),
       (3, 'wenyuan1', '123456', '/avatar.png', '13888888888', 1, NOW(), NOW(), NULL);
# 插入角色数据
INSERT INTO `role`
VALUES (1, '超级管理员', '系统最高权限者', NOW(), NOW(), NULL),
       (2, '管理员', '普通管理员', NOW(), NOW(), NULL),
       (3, '文员', '负责整理和录入资料', NOW(), NOW(), NULL);
# 插入权限数据
INSERT INTO `permission`
VALUES (1, '新增人员', '是否可以新增人员', 'USER.CREATE', NOW(), NOW(), NULL),
       (2, '更新人员', '是否可以更新人员', 'USER.UPDATE', NOW(), NOW(), NULL),
       (3, '删除/锁定人员', '是否可以删除/锁定人员', 'USER.DELETE', NOW(), NOW(), NULL);
# 插入角色权限中间表数据
INSERT INTO `associated_role_permission` (`role_id`, `permission_id`, `created_at`, `updated_at`) VALUES
(1, 1, NOW(), NOW()),
(1, 2, NOW(), NOW()),
(1, 3, NOW(), NOW()),
(2, 1, NOW(), NOW()),
(2, 3, NOW(), NOW()),
(3, 1, NOW(), NOW());
# 插入角色人员中间表数据
INSERT INTO `associated_role_user` (`role_id`, `user_id`, `created_at`, `updated_at`) VALUES
(1, 1, NOW(), NOW()),
(2, 2, NOW(), NOW()),
(3, 3, NOW(), NOW());