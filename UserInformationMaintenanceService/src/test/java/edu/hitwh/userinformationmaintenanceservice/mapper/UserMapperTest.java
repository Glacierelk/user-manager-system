package edu.hitwh.userinformationmaintenanceservice.mapper;

import edu.hitwh.userinformationmaintenanceservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    private final UserMapper userMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    void register() {
//        Boolean result = userMapper.register("test", "test");
//        assertEquals(true, result);
    }

    @Test
    void delete() {
        Boolean result = userMapper.delete(3);
        assertEquals(true, result);
    }
}