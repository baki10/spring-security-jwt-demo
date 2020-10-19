package com.bakigoal.securitydemo.service;

public interface SecuredWithRoleService {

    void forAdmins();

    void forUsers();

    void forAdminsOrUsers();
}
