package com.bakigoal.securitydemo.rest;

import com.bakigoal.securitydemo.model.User;
import com.bakigoal.securitydemo.security.jwt.JwtTokenProvider;
import com.bakigoal.securitydemo.service.SecuredWithRoleService;
import com.bakigoal.securitydemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AuthenticationRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private UserService userService;
    @MockBean
    private SecuredWithRoleService securedWithRoleService;

    @Test
    void successLoginTest() throws Exception {
        String username = "admin";
        String token = "123qwe";
        when(authenticationManager.authenticate(any())).thenReturn(authenticationStub());
        when(userService.findByUsername(username)).thenReturn(Optional.of(createUser(username)));
        when(jwtTokenProvider.createToken(any())).thenReturn(token);

        String request = "{\"username\":\"" + username + "\", \"password\":\"password\"}";
        mockMvc.perform(
                post("/api/v1/auth/login")
                        .content(request.getBytes(StandardCharsets.UTF_8))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"username\":\"" + username + "\"")))
                .andExpect(content().string(containsString("\"token\":\"" + token + "\"")));
    }

    @Test
    void usernameNotFoundTest() throws Exception {
        String username = "admin";
        String token = "123qwe";
        when(authenticationManager.authenticate(any())).thenReturn(authenticationStub());
        when(userService.findByUsername(username)).thenReturn(Optional.empty());
        when(jwtTokenProvider.createToken(any())).thenReturn(token);

        String request = "{\"username\":\"" + username + "\", \"password\":\"password\"}";
        mockMvc.perform(
                post("/api/v1/auth/login")
                        .content(request.getBytes(StandardCharsets.UTF_8))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("\"exception\":\"UsernameNotFoundException\"")));
    }

    private Authentication authenticationStub() {
        return new UsernamePasswordAuthenticationToken("admin", "");
    }

    private User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setId(1L);
        return user;
    }
}