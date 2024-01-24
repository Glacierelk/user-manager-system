package edu.hitwh.queryserviceconsumers.controller;

import edu.hitwh.queryserviceconsumers.entity.Resume;
import edu.hitwh.queryserviceconsumers.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserComprehensiveController {
    private final RestTemplate restTemplate;

    @Autowired
    public UserComprehensiveController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/detail/{user_id}")
    public UserDetail getUserDetailInfo(@PathVariable("user_id") Integer userId) {
        UserDetail user = restTemplate.getForEntity("http://query-service/user/info/get/" + userId, UserDetail.class).getBody();
        if (user == null)
            return null;
        ParameterizedTypeReference<List<Resume>> responseType = new ParameterizedTypeReference<>() { };
        List<Resume> resumes = restTemplate.exchange("http://query-service/user/resume/get/" + userId,
                HttpMethod.GET, null, responseType).getBody();
        user.setResumes(resumes);

        System.out.println(user);
        return user;
    }

}
