package edu.hitwh.userinformationmaintenanceservice.service;

import edu.hitwh.userinformationmaintenanceservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface UserService {
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return 是否注册成功
     */
    Boolean register(String username, String password);

    /**
     * 用户注销
     * @param userId 用户ID
     * @return 是否注销成功
     */
    Boolean delete(Integer userId);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 是否更新成功
     */
    Boolean updateInformation(User user);

    /**
     * 获取用户导入模板
     * @return 用户导入模板Excel
     */
    InputStream getTemplate() throws FileNotFoundException;

    /**
     * 导出用户信息
     * @return 用户信息Excel
     */
    InputStream export();

    /**
     * 批量导入用户
     * @param file 用户Excel
     * @return 是否导入成功
     */
    Boolean importUser(MultipartFile file);

    /**
     * 更新密码
     * @param id 用户 ID
     * @param password 密码
     * @return 是否更新成功
     */
    Boolean changePassword(Integer id, String password);
}
