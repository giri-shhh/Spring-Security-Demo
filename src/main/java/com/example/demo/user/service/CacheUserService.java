package com.example.demo.user.service;

import com.example.demo.config.log.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CacheUserService {

    @Cacheable(cacheNames = "users", key = "#userName")
    public Set<String> getUserRoles(String userName) {
        Logger.info("Accessed");
        Logger.debug(() -> "Hello");
        Logger.debug(() -> "User {0} has accessed cache");
        return Stream.of("WF_User", "WF_Admin").collect(Collectors.toSet());
    }
}
