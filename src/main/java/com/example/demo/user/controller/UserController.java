package com.example.demo.user.controller;

import com.example.demo.config.filter.FilterResponse;
import com.example.demo.user.dto.Price;
import com.example.demo.user.filter.FilterByName;
import com.example.demo.user.filter.FilterByPrice;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class UserController {

    public static final String ADMIN_WRITE_OR_STUDENT_READ = "(hasRole('ADMIN') and hasAuthority('course:write')) or ( hasRole('STUDENT') and hasAuthority('course:read'))";
    public static final String STUDENT_READ = "hasRole('STUDENT') and hasAuthority('student:read')";
    private final UserService userService;

    @GetMapping("/app")
    @PreAuthorize(ADMIN_WRITE_OR_STUDENT_READ)
    public List<String> courses() {
        return Stream.of("Java", "Javascript", "Python", "Go", "Rust")
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    @PreAuthorize(STUDENT_READ)
    @FilterResponse(filter = FilterByName.class)
    public String users() {
        return String.join("", "Giri", "Hari");
    }

    @GetMapping("/prices")
    @PreAuthorize(STUDENT_READ)
    @FilterResponse(filter = FilterByPrice.class)
    public List<Price> pricesNew() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return Stream.of(100, 200, 400, 500, 600, 700)
                .map(price -> new Price(price, "Price" + price))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/profiles")
    public Set<String> getUserDetails(@RequestParam(value = "refresh", defaultValue = "false") boolean refresh) {
        return userService.getUserRoles("Girish", refresh);

    }
}