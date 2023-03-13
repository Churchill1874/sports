package com.ent.sports.controller.player;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.sports.common.constant.LogTypeEnum;
import com.ent.sports.common.tools.GenerateTools;
import com.ent.sports.common.tools.HttpTools;
import com.ent.sports.service.EhcacheService;
import com.ent.sports.service.LogRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "验证码")
@RestController
@RequestMapping("/player/verificationCode")
public class VerificationCodeApi {

    @Autowired
    private EhcacheService ehcacheService;

    @Autowired
    private LogRecordService logRecordService;

    @PostMapping("/get")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    public synchronized R<String> get() {
        String verificationCode = GenerateTools.getVerificationCode();
        ehcacheService.getVerificationCodeCache().put(HttpTools.getIp(), verificationCode);
        logRecordService.insert(GenerateTools.createLog(LogTypeEnum.OPERATION,"获取验证码"));
        return R.ok(verificationCode);
    }

}
