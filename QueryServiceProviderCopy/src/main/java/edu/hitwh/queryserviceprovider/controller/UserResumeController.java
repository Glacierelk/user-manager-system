package edu.hitwh.queryserviceprovider.controller;

import edu.hitwh.queryserviceprovider.entity.Pagination;
import edu.hitwh.queryserviceprovider.entity.Resume;
import edu.hitwh.queryserviceprovider.service.UserResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/resume")
@CrossOrigin
public class UserResumeController {
    private final UserResumeService userResumeService;

    @Autowired
    public UserResumeController(UserResumeService userResumeService) {
        this.userResumeService = userResumeService;
    }

    @GetMapping("/get/{user_id}")
    public List<Resume> getResumesByUserId(@PathVariable("user_id") Integer userId) {
        return userResumeService.getResumesByUserId(userId);
    }

    @GetMapping("/list/{user_id}/{page_size}/{current_page}")
    public Pagination getResumesList(@PathVariable("user_id") Integer userId,
                                     @PathVariable("page_size") Integer pageSize,
                                     @PathVariable("current_page") Integer currentPage) {
        return userResumeService.getResumesList(userId, pageSize, currentPage);
    }
}
