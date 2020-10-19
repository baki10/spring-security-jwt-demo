package com.bakigoal.securitydemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Auth {

    public static AuthorizedUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthorizedUser)) {
            throw new IllegalStateException("Not authorized error");
        }
        return (AuthorizedUser) auth.getPrincipal();
    }
}
