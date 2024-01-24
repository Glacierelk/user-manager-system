package edu.hitwh.queryserviceprovider.controller;

import edu.hitwh.queryserviceprovider.entity.Pagination;
import edu.hitwh.queryserviceprovider.entity.User;
import edu.hitwh.queryserviceprovider.service.UserService;
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

    /**
     * 根据用户id获取用户信息
     * @param user_id 用户id
     * @return 用户信息
     */
    @GetMapping("/info/get/{user_id}")
    public User getUseInfo (@PathVariable("user_id") Integer user_id) {

        return userService.getUserInfo(user_id);
    }

    /**
     * 用户信息模糊查询
     * @param name 姓名
     * @param username 用户名
     * @param phone 电话
     * @param page_size 每页大小
     * @param current_page 当前页
     * @return 用户信息分页数据
     */
    @GetMapping("/list/{name}/{username}/{phone}/{page_size}/{current_page}")
    public Pagination getUserList (@PathVariable("name") String name,
                                   @PathVariable("username") String username,
                                   @PathVariable("phone") String phone,
                                   @PathVariable("page_size") Integer page_size,
                                   @PathVariable("current_page") Integer current_page) {
        return userService.getUserList(name, username, phone, page_size, current_page);
    }
}
