package com.bakigoal.securitydemo.rest;

import com.bakigoal.securitydemo.service.SecuredWithRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secured/role")
@RequiredArgsConstructor
public class SecuredWithRoleController {

    private final SecuredWithRoleService securedWithRoleService;

    @GetMapping("admin")
    public void admin() {
        securedWithRoleService.forAdmins();
    }
    @GetMapping("user")
    public void user() {
        securedWithRoleService.forUsers();
    }
    @GetMapping("admin-user")
    public void adminOrUser() {
        securedWithRoleService.forAdminsOrUsers();
    }
}
