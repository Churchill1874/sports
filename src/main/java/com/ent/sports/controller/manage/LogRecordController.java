package com.ent.sports.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.sports.common.annotation.AdminLoginCheck;
import com.ent.sports.entity.LogRecord;
import com.ent.sports.pojo.req.log.LogPageReq;
import com.ent.sports.service.LogRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "日志")
@RestController
@RequestMapping("/manage/logRecord")
public class LogRecordController {

    @Autowired
    private LogRecordService logRecordService;

    @AdminLoginCheck
    @PostMapping("/page")
    @ApiOperation(value = "分页日志", notes = "分页日志")
    public R<IPage<LogRecord>> page(@RequestBody @Valid LogPageReq req) {
        return R.ok(logRecordService.page(req));
    }

}
