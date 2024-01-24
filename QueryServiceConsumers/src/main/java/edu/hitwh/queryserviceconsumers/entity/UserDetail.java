package edu.hitwh.queryserviceconsumers.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    private Integer id;
    private String name;
    private String username;
    private String password;
    // 性别（0：女；1：男）
    private Integer gender;
    private String phone;
    private String email;
    private String createTime;
    private String updateTime;
    // 虚拟删除，0表示可用，1表示已删除
    private String deleteStatus;
    // 用户类型：0系统管理员；1人力资源主管；2普通用户
    private Integer userType;
    private List<Resume> resumes;
}
