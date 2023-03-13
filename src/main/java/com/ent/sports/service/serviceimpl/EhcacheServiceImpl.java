package com.ent.sports.service.serviceimpl;

import com.ent.sports.service.EhcacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * 该类对ehcache.xml配置文件里面已经配置的缓存容器进行实现获取，方便使用
 */
@Service
public class EhcacheServiceImpl implements EhcacheService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Cache getTokenCache() {
        return cacheManager.getCache("token");
    }

    @Override
    public Cache get3SecondLockCache() {
        return cacheManager.getCache("lock3Second");
    }

    @Override
    public Cache getVerificationCodeCache() {
        return cacheManager.getCache("verificationCode");
    }

    @Override
    public Cache getSmsVerificationCodeCache() {
        return cacheManager.getCache("smsVerificationCode");
    }

    @Override
    public Cache getBlacklistCache() {
        return cacheManager.getCache("blacklist");
    }

    @Override
    public Cache getFootballMatchCache() {
        return cacheManager.getCache("footballMatch");
    }


}
