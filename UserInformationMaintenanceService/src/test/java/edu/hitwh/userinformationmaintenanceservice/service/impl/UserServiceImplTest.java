package edu.hitwh.userinformationmaintenanceservice.service.impl;

import edu.hitwh.userinformationmaintenanceservice.entity.User;
import edu.hitwh.userinformationmaintenanceservice.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImplTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void register() {
        User user = userMapper.getUserByUsername("test001");
        System.out.println(user);
        if (user != null) {
            return;
        }
        user = new User();
        user.setUsername("test001");
        user.setPassword("123456");
        userMapper.register(user);
    }
}