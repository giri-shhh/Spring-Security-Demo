package com.example.demo.config.security.authorization;

import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationService {

    public boolean hasPermission(String role) {
        return true;
    }
}
