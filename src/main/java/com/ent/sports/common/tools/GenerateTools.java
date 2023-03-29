package com.ent.sports.common.tools;

import cn.hutool.Hutool;
import cn.hutool.core.util.RandomUtil;
import com.ent.sports.common.constant.LogTypeEnum;
import com.ent.sports.entity.LogRecord;
import com.ent.sports.entity.User;
import com.ent.sports.pojo.vo.Token;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * 生产工具
 */
public class GenerateTools {

    /**
     * 根据当前时间生成编号
     *
     * @return
     */
    public static String getTimeNo() {
        return LocalDateTime.now().toString()
                .replace(".", "")
                .replace("T", "")
                .replace(":", "")
                .replace("-", "");
    }

    /**
     * 生成五位验证码
     */
    public static String getVerificationCode() {
        int num = RandomUtil.randomInt(10000, 99999);
        return String.valueOf(num);
    }

    /**
     * 生成日志对象
     *
     * @param logTypeEnum 日志类型
     * @param message     日志内容
     * @return
     */
    public static LogRecord createLog(LogTypeEnum logTypeEnum, String message) {
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(HttpTools.getIp());
        logRecord.setType(logTypeEnum.getValue());
        logRecord.setMessage(message);
        logRecord.setAccount(TokenTools.getAccountMayNull());
        logRecord.setPlatform(HttpTools.getPlatform());
        return logRecord;
    }

    /**
     * 生成登录日志
     *
     * @return
     */
    public static LogRecord createLoginLog(Integer platform) {
        String ip = HttpTools.getIp();
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(ip);
        logRecord.setType(LogTypeEnum.LOGIN.getValue());
        logRecord.setMessage(HttpTools.getCityDataByIp(ip));
        logRecord.setAccount(TokenTools.getAccountMayNull());
        logRecord.setPlatform(platform);
        return logRecord;
    }

    /**
     * 传概念注册账号日志
     *
     * @param name
     * @param account
     * @return
     */
    public static LogRecord registerLog(String name, int account, int platform) {
        String ip = HttpTools.getIp();
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(ip);
        logRecord.setType(LogTypeEnum.REGISTER.getValue());
        logRecord.setMessage(name);
        logRecord.setAccount(account);
        logRecord.setPlatform(platform);
        return logRecord;
    }

    /**
     * 生产警告日志对象
     *
     * @param message
     * @return
     */
    public static LogRecord createWarnLog(String message) {
        String ip = HttpTools.getIp();
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(ip);
        logRecord.setType(LogTypeEnum.WARN.getValue());
        logRecord.setMessage(message);
        return logRecord;
    }

    /**
     * 生产警告日志对象
     *
     * @param message
     * @param ip
     * @return
     */
    public static LogRecord createWarnLog(String message, String ip, int platform) {
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(ip);
        logRecord.setType(LogTypeEnum.WARN.getValue());
        logRecord.setMessage(message);
        logRecord.setPlatform(platform);
        return logRecord;
    }


    /**
     * 生成token对象
     *
     * @param user
     * @return
     */
    public static Token createToken(User user) {
        Token token = new Token();
        token.setName(user.getName());
        token.setLoginTime(LocalDateTime.now());
        token.setRole(user.getRole());
        token.setAccount(user.getAccount());
        token.setId(user.getId());
        token.setStatus(user.getStatus());
        token.setPlatform(user.getPlatform());
        return token;
    }


    /**
     * 创建tokenId
     *
     * @param account
     * @return
     */
    public static String createTokenId(int account) {
        int accountLength = String.valueOf(account).length();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        uuid = uuid.substring(0, uuid.length() - accountLength);
        return uuid + account;
    }
}
