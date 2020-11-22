DROP DATABASE IF EXISTS `portal`;
CREATE DATABASE `portal` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci;
USE `portal`;


CREATE TABLE `user`
(
    `id`         BIGINT UNSIGNED AUTO_INCREMENT,
    `username`   VARCHAR(32) UNIQUE NOT NULL COMMENT '用户名',
    `password`   VARCHAR(32)        NOT NULL COMMENT '密码',
    `avatar`     VARCHAR(32)        NOT NULL COMMENT '头像',
    `phone`      VARCHAR(32)        NOT NULL COMMENT '手机号码',
    `enabled`    TINYINT  DEFAULT 1 COMMENT '启用: 0 否 1 是',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='人员表';

CREATE TABLE `role`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT,
    `name`        VARCHAR(32)  NOT NULL COMMENT '名称',
    `description` VARCHAR(128) NOT NULL COMMENT '描述',
    `enabled`     TINYINT  DEFAULT 1 COMMENT '启用: 0 否 1 是',
    `created_at`  DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='角色表';

CREATE TABLE `permission`
(
    `id`          BIGINT UNSIGNED AUTO_INCREMENT,
    `name`        VARCHAR(32)  NOT NULL COMMENT '名称',
    `description` VARCHAR(128) NOT NULL COMMENT '描述',
    `code`        VARCHAR(32)  NOT NULL COMMENT '对应的接口匹配代码',
    `enabled`     TINYINT  DEFAULT 1 COMMENT '启用: 0 否 1 是',
    `created_at`  DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at`  DATETIME DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='权限表';

CREATE TABLE `token`
(
    `id`         BIGINT UNSIGNED AUTO_INCREMENT,
    `type`       ENUM ('WEB', 'ANDROID', 'IOS') NOT NULL COMMENT '令牌类型',
    `token`      VARCHAR(255) COMMENT '对应token',
    `user_id`    BIGINT UNSIGNED COMMENT '人员ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='令牌表';

CREATE TABLE `associated_role_permission`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT,
    `role_id`       BIGINT UNSIGNED COMMENT '角色ID',
    `permission_id` BIGINT UNSIGNED COMMENT '权限ID',
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='角色权限中间表';

CREATE TABLE `associated_role_user`
(
    `id`      BIGINT UNSIGNED AUTO_INCREMENT,
    `role_id` BIGINT UNSIGNED COMMENT '角色ID',
    `user_id` BIGINT UNSIGNED COMMENT '人员ID',
    PRIMARY KEY (`id`)
) ENGINE = INNODB COMMENT ='角色人员中间表';

# 插入人员数据
INSERT INTO `user`
VALUES (1, 'admin', '123456', '/avatar.png', '13888888888', 1, NOW(), NOW(), NULL),
       (2, 'guanliyuan', '123456', '/avatar.png', '13888888888', 1, NOW(), NOW(), NULL),
       (3, 'wenyuan1', '123456', '/avatar.png', '13888888888', 1, NOW(), NOW(), NULL);
# 插入角色数据
INSERT INTO `role`
VALUES (1, '超级管理员', '系统最高权限者', 1, NOW(), NOW(), NULL),
       (2, '管理员', '普通管理员', 1, NOW(), NOW(), NULL),
       (3, '文员', '负责整理和录入资料', 1, NOW(), NOW(), NULL);
# 插入权限数据
INSERT INTO `permission`
VALUES (1, '查看人员', '是否可以查看人员', 'USER.DETAIL', 1, NOW(), NOW(), NULL),
       (2, '创建人员', '是否可以创建人员', 'USER.CREATE', 1, NOW(), NOW(), NULL),
       (3, '更新人员', '是否可以更新人员', 'USER.UPDATE', 1, NOW(), NOW(), NULL),
       (4, '删除/锁定人员', '是否可以删除/锁定人员', 'USER.DELETE', 1, NOW(), NOW(), NULL);
# 插入角色权限中间表数据
INSERT INTO `associated_role_permission` (`role_id`, `permission_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 3),
       (3, 1);
# 插入角色人员中间表数据
INSERT INTO `associated_role_user` (`role_id`, `user_id`)
VALUES (1, 1),
       (2, 2),
       (3, 3);