package com.douzon.smartlogistics.global.common.aop.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeTraceAspect {
    @Pointcut("@annotation(com.douzon.smartlogistics.global.common.aop.annotation.TimeTrace)")
    private void timer(){};

    @Around("timer()")
    public Object logTimeData(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object returnValue = joinPoint.proceed();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        long totalTimeMillis = System.currentTimeMillis() - startTime;

        log.info("실행 메서드: {}, 실행시간 = {}ms", methodName, totalTimeMillis);

        return returnValue;
    }
}
