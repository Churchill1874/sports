package com.ent.sports.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 角色枚举
 */
public enum RoleEnum {

    BOT(0,"机器人"),
    PLAYER(1,"玩家"),
    ADMIN(2,"管理员"),
    SUPER_ADMIN(3,"超级管理员");

    @Getter
    @JsonValue
    private final String name;

    @Getter
    @EnumValue
    private final int code;

    RoleEnum(int code,String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
