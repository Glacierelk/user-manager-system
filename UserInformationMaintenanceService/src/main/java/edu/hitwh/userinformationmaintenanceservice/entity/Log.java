package edu.hitwh.userinformationmaintenanceservice.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private Integer id;
    // 日志类型：0 用户注册；1 用户注销；
    // 2 用户修改基本信息；3 用户修改履历；
    // 4 用户登录；5 用户退出登录；6 用户查询信息
//    private Integer type;
    private Integer userId;
    private String description;
    private String createTime;
}
