package com.bakigoal.securitydemo.security.annotation;


import com.bakigoal.securitydemo.security.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Allow {

    /**
     * Список ролей, для которых доступен метод
     */
    UserRole[] roles();
}