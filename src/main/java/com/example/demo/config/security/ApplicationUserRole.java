package com.example.demo.config.security;

import com.example.demo.config.security.ApplicationUserAuthorities;
import com.google.common.collect.Sets;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.config.security.ApplicationUserAuthorities.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet(STUDENT_WRITE, STUDENT_READ, COURSE_READ)),
    ADMIN(Sets.newHashSet(STUDENT_READ, COURSE_READ, COURSE_WRITE));

    private final Set<ApplicationUserAuthorities> applicationUserAuthoritiesSet;

    ApplicationUserRole(Set<ApplicationUserAuthorities> userAuthorities) {
        this.applicationUserAuthoritiesSet = userAuthorities;
    }

    public Set<ApplicationUserAuthorities> getApplicationUserAuthoritiesSet() {
        return applicationUserAuthoritiesSet;
    }

    public Set<? extends GrantedAuthority> getUserGrantedAuthorities() {
        Set<SimpleGrantedAuthority> collect = getApplicationUserAuthoritiesSet()
                .stream()
                .map(ApplicationUserAuthorities::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        collect.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return collect;
    }
}
