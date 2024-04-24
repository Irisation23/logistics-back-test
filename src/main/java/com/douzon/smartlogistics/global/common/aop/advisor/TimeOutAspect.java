package com.douzon.smartlogistics.global.common.aop.advisor;

import com.douzon.smartlogistics.domain.receive.application.ReceiveService;
import com.douzon.smartlogistics.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TimeOutAspect {

    // 타임아웃 시간 설정 (8초를 넘어갈 경우 IOException 발생이 일어남으로 8초로 설정함)
    private static final long TIMEOUT = 8000;
    private final ReceiveService receiveService;
    @Pointcut("@annotation(com.douzon.smartlogistics.global.common.aop.annotation.TimeOut)")
    private void timeOut(){};

    @Around("timeOut()")
    public Object handleTimeout(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        Object responseData = ((CommonResponse<?>) ((ResponseEntity<?>) result).getBody()).getData();

        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;

        if (elapsedTime > TIMEOUT) {
            List<?> acceptData = null;
            if (responseData instanceof List<?>) {
                List<?> dataList = (List<?>) responseData;
                acceptData = dataList.subList(0, 500000);
            }
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(CommonResponse.successWith(acceptData));
        }

        return result;
    }
}
