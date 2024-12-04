package com.lion.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        // 클래스 명과 메소드
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();

        
        // 시간관련
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("Method: " + className + "." + methodName + "()" + " | startTime: " + startTime);

        Object result = joinPoint.proceed(); // primary concern 실행

        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("Method: " + className + "." + methodName + "()" + " | endTime: " + endTime);

        long duration = Duration.between(startTime, endTime).toMillis();
        System.out.println("Method: " + className + "." + methodName + "()" + " | Duration: " + duration + " ms");

        return  result;
    }
}
