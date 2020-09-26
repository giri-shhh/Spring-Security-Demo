package com.example.demo.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserService {

    @Cacheable(cacheNames = "users", key = "#userName")
    public Set<String> getUserRoles(String userName) {
        log.info("accessed");
        return Stream.of("WF_User", "WF_Admin")
                .collect(Collectors.toSet());
    }
}
