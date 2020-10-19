package com.bakigoal.securitydemo.service.impl;

import com.bakigoal.securitydemo.security.UserRole;
import com.bakigoal.securitydemo.security.annotation.Allow;
import com.bakigoal.securitydemo.service.SecuredWithRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecuredWithRoleServiceImpl implements SecuredWithRoleService {

    @Override
    @Allow(roles = {UserRole.ROLE_ADMIN})
    public void forAdmins() {
        log.info("onlyForAdmins invoked ...");
    }

    @Override
    @Allow(roles = {UserRole.ROLE_USER})
    public void forUsers() {
        log.info("onlyForUsers invoked ...");
    }

    @Override
    @Allow(roles = {UserRole.ROLE_ADMIN, UserRole.ROLE_USER})
    public void forAdminsOrUsers() {
        log.info("forAdminsOrUsers invoked ...");
    }
}
