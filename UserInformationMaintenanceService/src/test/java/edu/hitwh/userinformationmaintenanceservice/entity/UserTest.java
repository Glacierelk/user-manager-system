package edu.hitwh.userinformationmaintenanceservice.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Test
    void testLoginView() {
        User user = new User();
        user.setUsername("username");
        user.setId(1);
        user.setName("name");
        user.setPassword("sdfsdf");
        user.setEmail("sdfsf------");
        System.out.println(user);
    }
}