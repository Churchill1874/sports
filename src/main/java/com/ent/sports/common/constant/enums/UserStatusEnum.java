package com.ent.sports.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 用户状态枚举
 */
public enum UserStatusEnum {

    NORMAL("正常", 1),
    DISABLE("禁用", 0);

    @Getter
    @JsonValue
    private final String name;

    @Getter
    @EnumValue
    private final int code;

    UserStatusEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.name;
    }


}
