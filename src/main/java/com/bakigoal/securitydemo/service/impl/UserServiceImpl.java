package com.bakigoal.securitydemo.service.impl;

import com.bakigoal.securitydemo.model.EntityStatus;
import com.bakigoal.securitydemo.model.User;
import com.bakigoal.securitydemo.repository.RoleRepository;
import com.bakigoal.securitydemo.repository.UserRepository;
import com.bakigoal.securitydemo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        user.setStatus(EntityStatus.ACTIVE);

        User registeredUser = userRepository.save(user);
        log.info("Registered user {} successfully", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> all = userRepository.findAll();
        log.info("getAll {} found", all.size());
        return all;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("findById: No user found by id {}", id);
        } else {
            log.info("findById {} found by id {}", user, id);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info("findByUsername {} found by username {}", user, username);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("deleted user by id {}", id);

    }
}
