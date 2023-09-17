package com.ent.sports.common.aspect;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ent.sports.common.exception.BusinessException;
import com.ent.sports.common.tools.HttpTools;
import com.ent.sports.entity.Blacklist;
import com.ent.sports.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class BlacklistAspect {

    @Autowired
    private BlacklistService blacklistService;

    @Pointcut("execution(* com.ent.sports.controller.manage.*.*(..))")
    public void blacklistPointCut() {
    }

    @Before("blacklistPointCut()")
    public void beforeExecute() {
        String ip = HttpTools.getIp();
        List<Blacklist> list = blacklistService.findByIp(ip);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new BusinessException("ip已被限制,请联系管理员");
        }
    }

}
