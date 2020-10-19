package com.bakigoal.securitydemo.security;

import com.bakigoal.securitydemo.model.User;
import com.bakigoal.securitydemo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Qualifier("JwtUserDetailsService")
@Slf4j
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

        AuthorizedUser jwtUser = AuthorizedUser.from(user);
        log.info("loadUserByUsername {} loaded", username);
        return jwtUser;
    }
}
