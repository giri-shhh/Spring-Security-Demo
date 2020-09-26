package com.example.demo.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    public static final String USERS = "users";
    private final CacheManager cacheManager;
    private final CacheUserService cacheUserService;

    public Set<String> getUserRoles(String userName, boolean refresh) {
        if (refresh) evictCache(USERS, userName);
        return cacheUserService.getUserRoles(userName);
    }

    private void evictCache(String cacheName, String cacheKey) {
        Cache cache = cacheManager.getCache(cacheName);
        if (null != cache) cache.evict(cacheKey);
    }
}
