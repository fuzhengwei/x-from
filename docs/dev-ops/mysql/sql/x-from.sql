-- =====================================================
-- x-from 表单工程数据库初始化脚本
-- =====================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `x_from` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `x_from`;

-- =====================================================
-- 1. 表单表 - 存储表单的基本信息
-- =====================================================
DROP TABLE IF EXISTS `form`;
CREATE TABLE `form` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `form_code` VARCHAR(64) NOT NULL COMMENT '表单编码（唯一标识）',
    `form_name` VARCHAR(128) NOT NULL COMMENT '表单名称',
    `form_desc` VARCHAR(512) DEFAULT NULL COMMENT '表单描述',
    `form_config` TEXT NOT NULL COMMENT '表单配置JSON（字段定义）',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `creator` VARCHAR(64) NOT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_form_code` (`form_code`),
    KEY `idx_creator` (`creator`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='表单表';

-- =====================================================
-- 2. 表单提交记录表 - 存储用户填写的表单数据
-- =====================================================
DROP TABLE IF EXISTS `form_submission`;
CREATE TABLE `form_submission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `form_id` BIGINT NOT NULL COMMENT '表单ID',
    `form_code` VARCHAR(64) NOT NULL COMMENT '表单编码',
    `submission_data` TEXT NOT NULL COMMENT '提交数据JSON',
    `submitter` VARCHAR(64) DEFAULT NULL COMMENT '提交人（可选）',
    `submitter_ip` VARCHAR(64) DEFAULT NULL COMMENT '提交人IP',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_form_id` (`form_id`),
    KEY `idx_form_code` (`form_code`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='表单提交记录表';

-- =====================================================
-- 初始化测试数据
-- =====================================================

-- 插入一个测试表单
INSERT INTO `form` (`form_code`, `form_name`, `form_desc`, `form_config`, `status`, `creator`) VALUES
('test_form_001', '用户信息登记表', '用于收集用户的基本信息', 
'{
  "fields": [
    {
      "key": "name",
      "label": "姓名",
      "type": "text",
      "required": true,
      "placeholder": "请输入您的姓名"
    },
    {
      "key": "email",
      "label": "邮箱",
      "type": "email",
      "required": true,
      "placeholder": "请输入您的邮箱"
    },
    {
      "key": "phone",
      "label": "手机号",
      "type": "tel",
      "required": false,
      "placeholder": "请输入您的手机号"
    },
    {
      "key": "age",
      "label": "年龄",
      "type": "number",
      "required": false,
      "placeholder": "请输入您的年龄"
    },
    {
      "key": "comment",
      "label": "备注",
      "type": "textarea",
      "required": false,
      "placeholder": "请输入备注信息"
    }
  ]
}', 1, 'admin');

-- 插入一个测试提交记录
INSERT INTO `form_submission` (`form_id`, `form_code`, `submission_data`, `submitter`, `submitter_ip`) VALUES
(1, 'test_form_001', 
'{
  "name": "张三",
  "email": "zhangsan@example.com",
  "phone": "13800138000",
  "age": 28,
  "comment": "这是一个测试提交"
}', '张三', '192.168.1.100');
