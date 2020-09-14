package com.example.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DemoPasswordEncoder {

    @Bean
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
