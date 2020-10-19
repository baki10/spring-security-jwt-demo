package com.bakigoal.securitydemo.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class Auth {

    public static AuthorizedUser getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthorizedUser)) {
            throw new IllegalStateException("Not authorized error");
        }
        return (AuthorizedUser) auth.getPrincipal();
    }
}
