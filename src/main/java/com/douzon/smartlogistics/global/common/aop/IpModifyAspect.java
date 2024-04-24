package com.douzon.smartlogistics.global.common.aop;

import com.douzon.smartlogistics.domain.account.dto.AccountModifyDto;
import com.douzon.smartlogistics.domain.item.dto.ItemModifyDto;
import com.douzon.smartlogistics.domain.member.application.MemberService;
import com.douzon.smartlogistics.domain.porder.dto.POrderModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemModifyDto;
import com.douzon.smartlogistics.domain.porderitem.dto.POrderItemStateModifyDto;
import com.douzon.smartlogistics.domain.receive.dto.ReceiveModifyDto;
import com.douzon.smartlogistics.domain.receiveitem.dto.ReceiveItemModifyDto;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Aspect
@Component
@Order(1)
@SessionAttributes("session")
public class IpModifyAspect {

    private final HttpSession httpSession;
    private final MemberService memberService;

    @Autowired
    public IpModifyAspect(MemberService memberService, HttpSession httpSession) {
        this.httpSession = httpSession;
        this.memberService = memberService;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void PatchMapping() {
    }

    @Before("PatchMapping()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            handleModifyDto(arg);
        }
    }

    private void handleModifyDto(Object arg) {
        if (arg instanceof AccountModifyDto) {
            AccountModifyDto accountModifyDto = (AccountModifyDto) arg;
            accountModifyDto.setModifyId(getId());
            accountModifyDto.setModifyIp(getIpAddress());
        } else if (arg instanceof ItemModifyDto) {
            ItemModifyDto itemModifyDto = (ItemModifyDto) arg;
            itemModifyDto.setModifyId(getId());
            itemModifyDto.setModifyIp(getIpAddress());
        } else if (arg instanceof POrderModifyDto) {
            POrderModifyDto pOrderModifyDto = (POrderModifyDto) arg;
            pOrderModifyDto.setModifyId(getId());
            pOrderModifyDto.setModifyIp(getIpAddress());
        } else if (arg instanceof POrderItemModifyDto) {
            POrderItemModifyDto pOrderItemModifyDto = (POrderItemModifyDto) arg;
            pOrderItemModifyDto.setModifyId(getId());
            pOrderItemModifyDto.setModifyIp(getIpAddress());
        } else if (arg instanceof POrderItemStateModifyDto) {
            POrderItemStateModifyDto pOrderItemStateModifyDto = (POrderItemStateModifyDto) arg;
            pOrderItemStateModifyDto.setModifyId(getId());
            pOrderItemStateModifyDto.setModifyIp(getIpAddress());
        } else if (arg instanceof ReceiveModifyDto) {
            ReceiveModifyDto receiveModifyDto = (ReceiveModifyDto) arg;
            receiveModifyDto.setModifyId(getId());
            receiveModifyDto.setModifyIp(getIpAddress());
        } else if (arg instanceof ReceiveItemModifyDto) {
            ReceiveItemModifyDto receiveItemModifyDto = (ReceiveItemModifyDto) arg;
            receiveItemModifyDto.setModifyId(getId());
            receiveItemModifyDto.setModifyIp(getIpAddress());
        }
    }


    private String getIpAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("error message: {}", e.getMessage());
        }
        return "Failed to retrieve IP address";
    }

    private String getId() {
        return memberService.searchMember(
            (Long) httpSession.getAttribute("session")).getMemberId();
    }
}
