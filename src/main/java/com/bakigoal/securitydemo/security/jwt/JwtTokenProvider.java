package com.bakigoal.securitydemo.security.jwt;

import com.bakigoal.securitydemo.model.Role;
import com.bakigoal.securitydemo.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;
    @Value("${jwt.token.expired}")
    private Long expirationPeriodInMS;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Qualifier("CustomUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {
        var claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", getRoleNames(user.getRoles()));

        var now = new Date();
        var expiration = new Date(now.getTime() + expirationPeriodInMS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Optional<Authentication> getAuthentication(String token) {
        var username = validateTokenAndGetUsername(token);
        var user = userDetailsService.loadUserByUsername(username);
        if (!user.isEnabled()) {
            throw new JwtAuthenticationException("User with username " + username + " is not active");
        }
        return Optional.of(new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities()));
    }

    private String validateTokenAndGetUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token) // validating token
                .getBody().getSubject();
    }

    private List<String> getRoleNames(List<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
