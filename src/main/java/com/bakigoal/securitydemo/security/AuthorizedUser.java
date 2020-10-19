package com.bakigoal.securitydemo.security;

import com.bakigoal.securitydemo.model.EntityStatus;
import com.bakigoal.securitydemo.model.Role;
import com.bakigoal.securitydemo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class AuthorizedUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private final boolean enabled;
    private final Date lastPasswordResetDate;
    private final List<UserRole> userRoles;
    private final Collection<? extends GrantedAuthority> authorities;

    public static AuthorizedUser from(User user) {
        return new AuthorizedUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus().equals(EntityStatus.ACTIVE),
                user.getUpdated(),
                mapToUserRoleEnum(user.getRoles()),
                mapToGrantedAuthorities(user.getRoles()));
    }

    private static List<UserRole> mapToUserRoleEnum(List<Role> roles) {
        return roles.stream().map(role -> UserRole.valueOf(role.getName())).collect(Collectors.toList());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
