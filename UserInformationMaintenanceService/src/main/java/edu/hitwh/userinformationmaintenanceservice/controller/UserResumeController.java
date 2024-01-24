package edu.hitwh.userinformationmaintenanceservice.controller;

import edu.hitwh.userinformationmaintenanceservice.entity.Resume;
import edu.hitwh.userinformationmaintenanceservice.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user/resume")
public class UserResumeController {
    private final ResumeService resumeService;

    @Autowired
    public UserResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/add/{user_id}")
    public Boolean addResume(@PathVariable("user_id") Integer userId, @RequestBody Resume resume) {
        resume.setUserId(userId);
        return resumeService.addResume(resume);
    }

    @PostMapping("/update")
    public Boolean updateResume(@RequestBody Resume resume) {
        System.out.println(resume);
        return resumeService.updateResume(resume);
    }

    @DeleteMapping("/delete/{resume_id}")
    public Boolean deleteResume(@PathVariable("resume_id") Integer resumeId) {
        return resumeService.deleteResume(resumeId);
    }
}
