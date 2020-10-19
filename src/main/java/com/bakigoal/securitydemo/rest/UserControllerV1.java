package com.bakigoal.securitydemo.rest;

import com.bakigoal.securitydemo.dto.UserDto;
import com.bakigoal.securitydemo.security.Auth;
import com.bakigoal.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserControllerV1 {

    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        var user = userService.findById(id);
        var currentUser = Auth.getCurrentUser();
        log.info("Current User: {}", currentUser);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(UserDto.fromUser(user.get()), HttpStatus.OK);
    }
}
