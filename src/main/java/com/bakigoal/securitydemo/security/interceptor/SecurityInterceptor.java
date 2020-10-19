package com.bakigoal.securitydemo.security.interceptor;

import com.bakigoal.securitydemo.exception.AccessCheckException;
import com.bakigoal.securitydemo.security.Auth;
import com.bakigoal.securitydemo.security.annotation.Allow;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class SecurityInterceptor {
    /**
     * @param allow аннотация @Allow на проверяемом методе
     * @throws AccessCheckException если нет прав доступа к методу
     */
    @Before("execution(public * com.bakigoal.securitydemo.service..*(..)) && @annotation(allow)")
    public void checkPermissions(Allow allow) {
        var user = Auth.getCurrentUser();
        var userRoles = user.getUserRoles();
        var hasAccess = List.of(allow.roles()).stream().anyMatch(userRoles::contains);

        if (!hasAccess) {
            String errorMessage = String.format("User: %s - has NO access", user.getUsername());
            throw new AccessCheckException(errorMessage);
        }
        log.info("User: {} - has access", user.getUsername());
    }
}
