package com.ent.sports.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 赛事结果枚举
 */
public enum MatchResultEnum {

    DRAW(0, "平局"),
    WIN(1, "主队胜"),
    LOSE(2, "主队负");

    @Getter
    @JsonValue
    private final String name;

    @Getter
    @EnumValue
    private final int code;

    MatchResultEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
