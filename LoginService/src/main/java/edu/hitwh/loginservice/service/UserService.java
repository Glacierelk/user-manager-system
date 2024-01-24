package edu.hitwh.loginservice.service;

import edu.hitwh.loginservice.entity.User;

public interface UserService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 若成功, 则返回用户信息
     */
    User login(String username, String password) throws Exception;
}
