package com.bakigoal.securitydemo.security.jwt;

import com.bakigoal.securitydemo.model.Role;
import com.bakigoal.securitydemo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private Long expiredInMS;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Qualifier("JwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", getRoleNames(user.getRoles()));

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiredInMS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            return authHeader.substring(BEARER_TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            throw new JwtAuthenticationException("JWT token is invalid");
        }
    }

    private List<String> getRoleNames(List<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
