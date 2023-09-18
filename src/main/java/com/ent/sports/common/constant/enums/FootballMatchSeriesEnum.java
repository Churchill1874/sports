package com.ent.sports.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 足球赛事系列常量
 */
public enum FootballMatchSeriesEnum {

    WORLD_CUP(1, "WORLD CUP", "世界杯"),

    WORLD_CUP_WOMAN(2, "WORLD CUP WOMAN", "女足世界杯"),

    FIFA_WORLD_CUP_QUALIFIERS(3, "FIFA WORLD CUP QUALIFIERS", "世界杯预选赛"),

    FIFA_WORLD_CUP_QUALIFIERS_WOMAN(4, "FIFA WORLD CUP QUALIFIERS WOMAN", "女足世界杯预选赛"),

    IFC(5, "IFC", "国际友谊赛"),

    IFC_WOMAN(6, "IFC WOMAN", "女足国际友谊赛"),

    AFC_ASIAN_CUP(7, "AFC ASIAN CUP", "亚洲杯"),

    AFC_ASIAN_CUP_WOMAN(8, "AFC ASIAN CUP WOMAN", "女足亚洲杯"),

    AFC_ASIAN_CUP_QUALIFIERS(9, "AFC ASIAN CUP QUALIFIERS", "亚洲杯预选赛"),

    AFC_ASIAN_CUP_QUALIFIERS_WOMAN(10, "AFC ASIAN CUP QUALIFIERS WOMAN", "女足亚洲杯预选赛"),

    SPANISH_LA_LIGA(11, "SPANISH LA LIGA", "西甲"),

    ENGLISH_PREMIER_LEAGUE(12, "ENGLISH PREMIER LEAGUE", "英超"),

    SERIE_A(13, "SERIE A", "意甲"),

    EREDIVISIE(14, "EREDIVISIE", "荷甲"),

    BUNDESLIGA(15, "BUNDESLIGA", "德甲"),

    PRIMEIRA_LIGA(16, "PRIMEIRA LIGA", "葡甲"),

    LIGUE_1(17, "LIGUE 1", "法甲"),

    BRAZILIAN_SERIE_A(18, "BRAZILIAN SERIE A", "巴甲"),

    CHINESE_SUPER_LEAGUE(19, "CHINESE SUPER LEAGUE", "中超");

    @Getter
    @EnumValue
    private final int code;

    @Getter
    @JsonValue
    private final String chinese;

    @Getter
    private final String english;

    FootballMatchSeriesEnum(int code, String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
        this.code = code;
    }

    /**
     * 根据编码查找枚举
     * @param code
     * @return
     */
    public FootballMatchSeriesEnum findByChinese(int code) {
        for (FootballMatchSeriesEnum footballMatchSeriesEnum : FootballMatchSeriesEnum.values()) {
            if (footballMatchSeriesEnum.getCode() == code) {
                return footballMatchSeriesEnum;
            }
        }
        return null;
    }

    /**
     * 根据赛事系列中文查找枚举
     *
     * @param chinese
     * @return
     */
    public FootballMatchSeriesEnum findByChinese(String chinese) {
        for (FootballMatchSeriesEnum footballMatchSeriesEnum : FootballMatchSeriesEnum.values()) {
            if (footballMatchSeriesEnum.getChinese().equals(chinese)) {
                return footballMatchSeriesEnum;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.code + ":" + this.english + ":" + this.chinese;
    }

}
