package com.ent.sports.common.tools;

import com.ent.sports.common.exception.BusinessException;
import com.ent.sports.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * 编码处理工具
 */
public class CodeTools {

    private static final String SALT = "fk";

    public static String md5AndSalt(String password){
        if (StringUtils.isBlank(password)){
            throw new BusinessException("加盐密码不能为空");
        }
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes());
    }


}
