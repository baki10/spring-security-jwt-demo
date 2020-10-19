package com.bakigoal.securitydemo.service;

import com.bakigoal.securitydemo.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findById(Long id);

    User findByUsername(String username);

    void delete(Long id);
}
