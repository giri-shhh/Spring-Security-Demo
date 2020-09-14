package com.example.demo.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UserService {

    @Cacheable(cacheNames = "users", key = "#userName")
    public Set<String> getUserRoles(String userName) {
        log.info("accessed");
        return Stream.of("WF_User", "WF_Admin")
                .collect(Collectors.toSet());
    }

    @Cacheable(cacheNames = "user.authorities", key = "#userName")
    public Set<SimpleGrantedAuthority> getUserGrantedAuthorities(String userName) {
        return Stream.of("WF_User", "WF_Admin")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return User.builder()
                .username(userName)
                .password("pass")
                .roles(getUserRoles(userName).toArray(String[]::new))
                .authorities(getUserGrantedAuthorities(userName))
                .build();
    }
}
