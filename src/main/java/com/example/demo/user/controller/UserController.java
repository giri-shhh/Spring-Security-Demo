package com.example.demo.user.controller;

import com.example.demo.config.filter.FilterResponse;
import com.example.demo.user.service.FilterByName;
import com.example.demo.user.service.FilterByPrice;
import com.example.demo.user.service.Price;
import com.example.demo.user.service.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/")
    public List<String> hello() {
        return Stream.of("05Hi", "Hello", "Hi", "05hhhh", "HI", "HELLO")
                .collect(Collectors.toList());
    }


    @GetMapping("/app")
    @PreAuthorize("(hasRole('ADMIN') and hasAuthority('course:write')) " +
            "or ( hasRole('STUDENT') and hasAuthority('course:read'))")
    public List<String> courses() {
        return Stream.of("Java", "Javascript", "Python", "Go", "Rust")
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    @FilterResponse(filter = FilterByName.class)
    @PreAuthorize("hasRole('STUDENT') and hasAuthority('student:read')")
    public List<User> users() {
        return Stream.of("Girish", "Harish", "Prakash", "Varun")
                .map(User::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/prices")
    @FilterResponse(filter = FilterByPrice.class)
    @PreAuthorize("hasRole('STUDENT') and hasAuthority('student:read')")
    public List<Price> prices() {
        return Stream.of(100, 200, 400, 500, 600, 700)
                .map(Price::new)
                .collect(Collectors.toList());
    }
}
