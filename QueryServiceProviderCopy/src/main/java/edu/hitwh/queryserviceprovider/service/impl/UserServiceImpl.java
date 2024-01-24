package edu.hitwh.queryserviceprovider.service.impl;

import edu.hitwh.queryserviceprovider.entity.Pagination;
import edu.hitwh.queryserviceprovider.entity.User;
import edu.hitwh.queryserviceprovider.mapper.UserMapper;
import edu.hitwh.queryserviceprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserInfo(Integer userId) {
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public Pagination getUserList(String name, String username, String phone, Integer pageSize, Integer currentPage) {
        if (name.equals("null")) {
            name = null;
        }
        if (username.equals("null")) {
            username = null;
        }
        if (phone.equals("null")) {
            phone = null;
        }
        Pagination pagination = new Pagination();
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(currentPage);
        pagination.setTotalCount(userMapper.getUserCount(name, username, phone));
        pagination.setList(userMapper.getUserList(name, username, phone, (currentPage - 1) * pageSize, pageSize));
        return pagination;
    }
}
