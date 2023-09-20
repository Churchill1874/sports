package com.ent.sports.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 比赛状态枚举
 */
public enum MatchStatusEnum {

    NOT_START(0,"未开始"),
    IN_PROGRESS(1,"比赛中"),
    FINISHED(2,"比赛结束"),
    CANCEL(3,"取消");

    @Getter
    @JsonValue
    private final String name;

    @Getter
    @EnumValue
    private final int code;

    MatchStatusEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.code + ":" + this.name;
    }

}
