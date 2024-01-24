package edu.hitwh.queryserviceprovider.logging;

import edu.hitwh.queryserviceprovider.entity.Log;
import edu.hitwh.queryserviceprovider.mapper.LogMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerAspect {
    private final HttpServletRequest request;
    private final LogMapper logMapper;

    @Autowired
    public ControllerAspect(HttpServletRequest request, LogMapper logMapper) {
        this.request = request;
        this.logMapper = logMapper;
    }

    @Before("execution(* edu.hitwh.queryserviceprovider.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        String userIdString = request.getHeader("User-Id");
        Integer userId = userIdString == null ? null : Integer.parseInt(userIdString);

        Log log = new Log();
        log.setUserId(userId);
        log.setDescription("Method invoked: " + methodName + ", Arguments: " + Arrays.toString(args));
        logMapper.insertLog(log);
    }
}
