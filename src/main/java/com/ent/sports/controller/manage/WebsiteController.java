package com.ent.sports.controller.manage;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.sports.common.constant.enums.RoleEnum;
import com.ent.sports.common.exception.AccountOrPasswordException;
import com.ent.sports.common.exception.AuthException;
import com.ent.sports.common.tools.CodeTools;
import com.ent.sports.common.tools.GenerateTools;
import com.ent.sports.common.tools.HttpTools;
import com.ent.sports.common.tools.TokenTools;
import com.ent.sports.entity.User;
import com.ent.sports.pojo.req.website.LoginManageReq;
import com.ent.sports.pojo.vo.Token;
import com.ent.sports.service.EhcacheService;
import com.ent.sports.service.LogRecordService;
import com.ent.sports.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "登录")
@RequestMapping("/manage/website")
public class WebsiteController {

    @Autowired
    private EhcacheService ehcacheService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogRecordService logRecordService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public R<String> login(@RequestBody @Valid LoginManageReq req) {

        //校验验证码
        String verificationCode = ehcacheService.getVerificationCodeCache().get(HttpTools.getIp(), String.class);
        if (verificationCode == null) {
            return R.failed("验证码有误或已过期");
        }

        //判断账号密码是否正确
        User user = userService.findByAccount(req.getAccount());
        if (user == null) {
            throw new AccountOrPasswordException();
        }

        //对比登录密码和正确密码
        String password = user.getPassword();
        String passwordReq = CodeTools.md5AndSalt(req.getPassword());

        if (!password.equals(passwordReq)) {
            throw new AccountOrPasswordException();
        }

        //判断角色是否是管理员
        if (user.getRole() != RoleEnum.ADMIN.getCode() && user.getRole() != RoleEnum.SUPER_ADMIN.getCode()) {
            throw new AuthException();
        }

        //校验是否已经登录,如果已经登陆过删除之前的tokenId和缓存
        checkLoginCache(user.getAccount());

        //生成token并返回
        Token token = GenerateTools.createToken(user);
        String tokenId = GenerateTools.createTokenId(user.getAccount());
        ehcacheService.getTokenCache().put(tokenId, token);

        //记录登录日志
        logRecordService.insert(GenerateTools.createLoginLog(token.getPlatform()));

        //删除使用过的验证码缓存
        ehcacheService.getVerificationCodeCache().evict(HttpTools.getIp());
        return R.ok(tokenId);
    }

    //如果当前登录的账号已经是登陆状态 则删除之前的登录缓存
    private void checkLoginCache(int account) {
        Cache c = (Cache) ehcacheService.getTokenCache().getNativeCache();
        List<String> list = c.getKeys();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(tokenId -> {
                if (tokenId.contains(String.valueOf(account))) {
                    ehcacheService.getTokenCache().evict(tokenId);
                }
            });
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public R logout() {
        ehcacheService.getTokenCache().evict(TokenTools.getToken().getAccount());
        return R.ok(null);
    }

    @PostMapping("/getAllToken")
    @ApiOperation(value = "获取所有登陆人", notes = "获取所有登陆人")
    public R<List<User>> getAllToken() {
        net.sf.ehcache.Cache c = (net.sf.ehcache.Cache) ehcacheService.getTokenCache().getNativeCache();
        List<String> list = c.getKeys();
        List<Long> idList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(tokenId -> {
                Token token = ehcacheService.getTokenCache().get(tokenId, Token.class);

                if (token != null) {
                    idList.add(token.getId());
                }

            });
        }

        if (!CollectionUtils.isEmpty(idList)) {
            List<User> userList = (List<User>) userService.listByIds(idList);
            return R.ok(userList);
        }

        return R.ok(null);
    }


}
