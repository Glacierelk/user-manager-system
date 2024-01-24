package edu.hitwh.userinformationmaintenanceservice.controller;

import edu.hitwh.userinformationmaintenanceservice.entity.User;
import edu.hitwh.userinformationmaintenanceservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody User user) {
        return userService.register(user.getUsername(), user.getPassword());
    }

    @PutMapping("/change_pwd")
    public Boolean changePassword(@RequestBody User user) {
        return userService.changePassword(user.getId(), user.getPassword());
    }

    @DeleteMapping("/delete/{user_id}")
    public Boolean delete(@PathVariable("user_id") Integer userId) {
        return userService.delete(userId);
    }

    @PostMapping("/info/update")
    public Boolean updateInformation(@RequestBody User user) {
        return userService.updateInformation(user);
    }

    /**
     * 获取用户导入模板
     * @return 用户导入模板Excel
     */
    @GetMapping("/template")
    public ResponseEntity<InputStreamResource> downloadTemplate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        try {
            String encodedFileName = URLEncoder.encode("用户导入模板.xlsx", StandardCharsets.UTF_8);
            headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        } catch (Exception e) {
            headers.add("Content-Disposition", "attachment; filename=students_template.xlsx");
        }

        try {
            InputStream inputStream = userService.getTemplate();
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(inputStreamResource);
        } catch (Exception e) {
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    /**
     * 批量导出用户信息
     * @return 用户信息Excel
     */
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportUserInfo(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        try {
            String encodedFileName = URLEncoder.encode("用户信息.xlsx", StandardCharsets.UTF_8);
            headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        } catch (Exception e) {
            headers.add("Content-Disposition", "attachment; filename=course_student_emails.xlsx");
        }

        InputStream inputStream = userService.export();
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .headers(headers)
                .body(inputStreamResource);
    }

    @PostMapping("/import")
    public Map<String, Object> importStudent(@RequestParam("users") MultipartFile file){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("flag", userService.importUser(file));
        } catch (Exception e) {
            result.put("flag", false);
            result.put("errorMsg", e.getMessage());
        }
        return result;
    }

}
