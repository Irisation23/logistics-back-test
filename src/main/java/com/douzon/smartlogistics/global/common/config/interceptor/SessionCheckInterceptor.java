package com.douzon.smartlogistics.global.common.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        System.out.println("이동 전 세션 체크 session = " + session);

        if(session == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
//            response.getWriter().write("Session expired");
            return false; // 중요: 이후 핸들러나 인터셉터 체인을 실행하지 않음
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
