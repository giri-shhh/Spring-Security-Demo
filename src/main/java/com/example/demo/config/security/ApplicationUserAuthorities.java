package com.example.demo.config.security;

public enum ApplicationUserAuthorities {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String authority;

    ApplicationUserAuthorities(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
