package com.ent.sports.common.aspect;

import com.ent.sports.common.constant.enums.RoleEnum;
import com.ent.sports.common.constant.enums.UserStatusEnum;
import com.ent.sports.common.exception.AuthException;
import com.ent.sports.common.tools.TokenTools;
import com.ent.sports.pojo.vo.Token;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PlayerLoginCheck {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.ent.sports.common.annotation.PlayerLoginCheck)")
    public void playerLoginCheck() {

    }

    @Before("playerLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
        Token token = TokenTools.getToken();
        if (token.getRole() != RoleEnum.PLAYER.getCode() || token.getStatus() == UserStatusEnum.DISABLE) {
            throw new AuthException();
        }
    }


}
