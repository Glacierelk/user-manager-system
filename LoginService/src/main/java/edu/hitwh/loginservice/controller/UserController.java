package edu.hitwh.loginservice.controller;

import edu.hitwh.loginservice.entity.User;
import edu.hitwh.loginservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestBody User user, HttpServletResponse response) {
        try {
            return userService.login(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            if (e.getMessage().equals("0"))
                response.setStatus(500);
            else
                response.setStatus(501);
            return null;
        }
    }

}
