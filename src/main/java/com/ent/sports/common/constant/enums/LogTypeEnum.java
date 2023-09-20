package com.ent.sports.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 日志枚举
 */
public enum LogTypeEnum {

    ERROR(1, "异常日志"),
    OPERATION(2, "操作日志"),
    LOGIN(3, "登录日志"),
    REGISTER(4, "注册日志"),
    WARN(5,"警告日志");

    @Getter
    @JsonValue
    private final String name;

    @Getter
    @EnumValue
    private final int code;

    LogTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.name;
    }

}
