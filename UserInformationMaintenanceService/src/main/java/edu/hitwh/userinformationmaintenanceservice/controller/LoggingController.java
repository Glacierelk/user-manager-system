package edu.hitwh.userinformationmaintenanceservice.controller;

import edu.hitwh.userinformationmaintenanceservice.entity.Pagination;
import edu.hitwh.userinformationmaintenanceservice.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin
@RequestMapping("/logging")
public class LoggingController {
    private final LoggingService loggingService;

    @Autowired
    public LoggingController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping("/list/{page_size}/{current_page}/{start_time}/{end_time}")
    public Pagination getLogs(@PathVariable("page_size") Integer pageSize,
                              @PathVariable("current_page") Integer currentPage,
                              @PathVariable("start_time") String startTime,
                              @PathVariable("end_time") String endTime) {
        return loggingService.queryLogs(startTime, endTime, currentPage, pageSize);
    }

    @GetMapping("/export/{start_time}/{end_time}")
    public ResponseEntity<InputStreamResource> exportLogs(@PathVariable(value = "start_time") String startTime,
                                                          @PathVariable(value = "end_time") String endTime){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        try {
            String encodedFileName = URLEncoder.encode("日志.xlsx", StandardCharsets.UTF_8);
            headers.add("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        } catch (Exception e) {
            headers.add("Content-Disposition", "attachment; filename=course_student_emails.xlsx");
        }

        InputStream inputStream = loggingService.export(startTime, endTime);
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .headers(headers)
                .body(inputStreamResource);
    }
}
