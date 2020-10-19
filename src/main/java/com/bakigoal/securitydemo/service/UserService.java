package com.bakigoal.securitydemo.service;

import com.bakigoal.securitydemo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(User user);

    List<User> getAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void delete(Long id);
}
