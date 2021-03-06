package com.example.demo.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import static com.example.demo.config.security.ApplicationUserRole.ADMIN;
import static com.example.demo.config.security.ApplicationUserRole.STUDENT;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails giri = User.builder()
                .username("giri")
                .password(passwordEncoder.encode("pass"))
                .authorities(ADMIN.getUserGrantedAuthorities())
                .build();

        UserDetails hari = User.builder()
                .username("hari")
                .password(passwordEncoder.encode("pass"))
                .authorities(STUDENT.getUserGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(giri, hari);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SwitchUserFilter getSwitch() {
        SwitchUserFilter switchUserFilter = new SwitchUserFilter();
        switchUserFilter.setSwitchUserUrl("/pwm/atom/user/switch");
        switchUserFilter.setUserDetailsService(userDetailsService());
        switchUserFilter.setExitUserUrl("/pwm/atom/user/exit-switch");
        switchUserFilter.setSwitchAuthorityRole("ADMIN");
        switchUserFilter.setUsernameParameter("Impersonate-User-Id");
        switchUserFilter.setSuccessHandler((request, response, authentication) -> System.out.println("successfully impersonated"));
        return switchUserFilter;
    }
}