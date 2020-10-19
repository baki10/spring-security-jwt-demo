package com.bakigoal.securitydemo.rest;

import com.bakigoal.securitydemo.dto.UserDto;
import com.bakigoal.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class AdminControllerV1 {

    private final UserService userService;

    @GetMapping("users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        var user = userService.findById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(UserDto.fromUser(user.get()), HttpStatus.OK);
    }
}
