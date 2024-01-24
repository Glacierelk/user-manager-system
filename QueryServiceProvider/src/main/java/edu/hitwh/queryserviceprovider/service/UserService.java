package edu.hitwh.queryserviceprovider.service;

import edu.hitwh.queryserviceprovider.entity.Pagination;
import edu.hitwh.queryserviceprovider.entity.User;

import java.util.List;

public interface UserService {
    /**
     * Get user info by user id
     * @param userId user id
     * @return user info
     */
    User getUserInfo(Integer userId);

    /**
     * Get user list by name, username, phone, page size and current page [人力资源主管]
     * @param name 用户姓名
     * @param username 用户名
     * @param phone 手机号
     * @param pageSize 每页显示的条数
     * @param currentPage 当前页
     * @return 用户列表
     */
    Pagination getUserList(String name, String username, String phone, Integer pageSize, Integer currentPage);
}
