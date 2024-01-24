package edu.hitwh.loginservice.logging;

import edu.hitwh.loginservice.entity.Log;
import edu.hitwh.loginservice.entity.User;
import edu.hitwh.loginservice.mapper.LogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
    private final LogMapper logMapper;

    @Autowired
    public ServiceAspect (LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @AfterReturning(pointcut = "execution(* edu.hitwh.loginservice.service.UserService.login(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        Log log = new Log();
        if (result != null) {
            System.out.println("Login successful. User details: " + result);
            User user = (User) result;
            log.setUserId(user.getId());
            log.setDescription(user.getUsername() + " login successful.");
        } else {
            log.setDescription(args[0] + " login failed.");
        }
        logMapper.insertLog(log);
    }

    @AfterThrowing(pointcut = "execution(* edu.hitwh.loginservice.service.UserService.login(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        Object[] args = joinPoint.getArgs();
        Log log = new Log();
        log.setDescription(args[0] + " login failed, error code:" + exception.getMessage());
        logMapper.insertLog(log);
    }
}
