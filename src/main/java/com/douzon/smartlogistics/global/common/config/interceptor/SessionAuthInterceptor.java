package com.douzon.smartlogistics.global.common.config.interceptor;

import com.douzon.smartlogistics.domain.member.application.MemberService;
import com.douzon.smartlogistics.domain.entity.Member;
import com.douzon.smartlogistics.global.common.exception.auth.Auth;
import com.douzon.smartlogistics.global.common.exception.auth.AuthException;
import com.douzon.smartlogistics.global.common.exception.auth.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Component
public class SessionAuthInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            // 리소스 요청은 인증 처리하지 않음
            return true;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Auth authAnnotation = handlerMethod.getMethodAnnotation(Auth.class);

            if (authAnnotation == null) {
                // Auth 어노테이션이 없는 경우에는 인증 처리하지 않음
                return true;
            }

            HttpSession session = request.getSession();
            Object memberNo = session.getAttribute("session");

            if (memberNo == null) {
                throw new AuthException("not logged in");
            }

            Member member = memberService.searchMember((Long) memberNo);

            if ("ADMIN".equals(member.getMemberRole())) {
                return true;
            } else {
                throw new ForbiddenException();
            }
        }

        // 인증 처리하지 않은 경우
        return true;
    }



    private boolean checkAnnotation(Object handler, Class<Auth> authClass) {
        // js. html 타입인 view 과련 파일들은 통과한다.(view 관련 요청 = ResourceHttpRequestHandler)
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // Auth anntotation이 있는 경우
        if (null != handlerMethod.getMethodAnnotation(authClass) || null != handlerMethod.getBeanType().getAnnotation(authClass)) {
            return true;
        }

        // annotation이 없는 경우
        return false;
    }

}
