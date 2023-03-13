package com.ent.sports.common.tools;

import com.ent.sports.common.constant.LogTypeEnum;
import com.ent.sports.entity.LogRecord;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * 生产工具
 */
public class GenerateTools {

    /**
     * 生成指定范围随机数
     *
     * @param start 最大值
     * @param end   最小值
     * @return
     */
    public static int getRandom(int start, int end) {
        Random random = new Random();
        return random.nextInt((end - start) + 1) + start;
    }

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
        int num = getRandom(10000, 99999);
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
        logRecord.setRequestUrl(HttpTools.getRequest().getRequestURI());
        logRecord.setMessage(message);
        logRecord.setAccount(TokenTools.getAccountMayNull());
        return logRecord;
    }

    /**
     * 生成登录日志
     *
     * @return
     */
    public static LogRecord createLoginLog() {
        String ip = HttpTools.getIp();
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(ip);
        logRecord.setType(LogTypeEnum.LOGIN.getValue());
        logRecord.setRequestUrl(HttpTools.getRequest().getRequestURI());
        logRecord.setMessage(HttpTools.getCityDataByIp(ip));
        logRecord.setAccount(TokenTools.getAccountMayNull());
        return logRecord;
    }

    /**
     * 传概念注册账号日志
     *
     * @param name
     * @param account
     * @return
     */
    public static LogRecord registerLog(String name, int account) {
        String ip = HttpTools.getIp();
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(ip);
        logRecord.setType(LogTypeEnum.REGISTER.getValue());
        logRecord.setRequestUrl(HttpTools.getRequest().getRequestURI());
        logRecord.setMessage(name);
        logRecord.setAccount(account);
        return logRecord;
    }

    /**
     * 创建token
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
        logRecord.setRequestUrl(HttpTools.getRequest().getRequestURI());
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
    public static LogRecord createWarnLog(String message, String ip) {
        LogRecord logRecord = new LogRecord();
        logRecord.setIp(ip);
        logRecord.setType(LogTypeEnum.WARN.getValue());
        logRecord.setRequestUrl(HttpTools.getRequest().getRequestURI());
        logRecord.setMessage(message);
        return logRecord;
    }

}
