package edu.hitwh.loginservice.service.impl;

import edu.hitwh.loginservice.entity.User;
import edu.hitwh.loginservice.mapper.UserMapper;
import edu.hitwh.loginservice.service.UserService;
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
    public User login(String username, String password) throws Exception {
        User user = userMapper.getUserByUsername(username);
        if (user == null)
            throw new Exception("0");
        if (user.getPassword().equals(password))
            return user;
        throw new Exception("1");
    }
}
