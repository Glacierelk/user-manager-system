package edu.hitwh.loginservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    // 用户类型：0系统管理员；1人力资源主管；2普通用户
    private Integer userType;
}
