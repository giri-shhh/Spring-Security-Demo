package com.example.demo.config.security;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Order(OrderedFilter.HIGHEST_PRECEDENCE)
public class SwitchUserFilter extends GenericFilterBean {

    public static final String ROLE_PREVIOUS_ADMINISTRATOR = "ROLE_PREVIOUS_ADMINISTRATOR";
    public static final String IMPERSONATE_USER_ID = "Impersonate-User-Id";
    private final UserDetailsService userDetailsService;

    public SwitchUserFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String username = request.getHeader(IMPERSONATE_USER_ID);
        if (!StringUtils.isEmpty(username)) {
            try {
                Authentication targetUser = attemptSwitchUser(username);
                SecurityContextHolder.getContext().setAuthentication(targetUser);
            } catch (AuthenticationException e) {
                this.logger.debug("Switch User failed", e);
            }
        }
        chain.doFilter(request, response);
    }

    protected Authentication attemptExitUser()
            throws AuthenticationCredentialsNotFoundException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (null == currentUser)
            throw new AuthenticationCredentialsNotFoundException("No current user associated with this request");
        return currentUser;
    }

    private UsernamePasswordAuthenticationToken attemptSwitchUser(
            String impersonateUserName) {

        UserDetails targetUser = this.userDetailsService.loadUserByUsername(impersonateUserName);

        Authentication currentAuth = attemptExitUser();
        Collection<? extends GrantedAuthority> orig = targetUser.getAuthorities();

        List<GrantedAuthority> newAuths = new ArrayList<>(orig);

        GrantedAuthority switchAuthority = new SwitchUserGrantedAuthority(
                ROLE_PREVIOUS_ADMINISTRATOR, currentAuth);

        newAuths.add(switchAuthority);

        return new UsernamePasswordAuthenticationToken(
                targetUser, targetUser.getPassword(), newAuths);
    }
}
