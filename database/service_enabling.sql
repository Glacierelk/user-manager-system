
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `l_id` int NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `l_user_id` int NULL DEFAULT NULL COMMENT '执行操作的用户ID',
  `l_description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作涉及到的内容',
  `l_create_time` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`l_id`) USING BTREE,
  INDEX `foreign_key_log_user_id`(`l_user_id` ASC) USING BTREE,
  CONSTRAINT `foreign_key_log_user_id` FOREIGN KEY (`l_user_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 575 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resume
-- ----------------------------
DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume`  (
  `r_id` int NOT NULL AUTO_INCREMENT COMMENT '履历ID',
  `r_user_id` int NOT NULL COMMENT '用户ID外键',
  `r_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '履历描述',
  `r_start_date` datetime NOT NULL COMMENT '开始时间',
  `r_end_date` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `r_insert` timestamp NOT NULL COMMENT '创建时间',
  `r_update` timestamp NOT NULL COMMENT '修改时间',
  `r_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '虚拟删除，0表示可用，1表示已删除',
  PRIMARY KEY (`r_id`) USING BTREE,
  INDEX `foreign_key_resume_user_id`(`r_user_id` ASC) USING BTREE,
  CONSTRAINT `foreign_key_resume_user_id` FOREIGN KEY (`r_user_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `u_id` int NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
  `u_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `u_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `u_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `u_gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别（0：女；1：男）',
  `u_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `u_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `u_insert` timestamp NOT NULL COMMENT '账户创建时间',
  `u_update` timestamp NOT NULL COMMENT '信息更新时间',
  `u_delete` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '虚拟删除，0表示可用，1表示已删除',
  `u_type` int NOT NULL COMMENT '用户类型：0系统管理员；1人力资源主管；2普通用户',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for user_account_view
-- ----------------------------
DROP VIEW IF EXISTS `user_account_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_account_view` AS select `user`.`u_id` AS `user_id`,`user`.`u_username` AS `username`,`user`.`u_password` AS `password`,`user`.`u_type` AS `user_type` from `user` where (`user`.`u_delete` = 0);

SET FOREIGN_KEY_CHECKS = 1;
