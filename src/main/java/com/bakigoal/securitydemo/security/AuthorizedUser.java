package com.bakigoal.securitydemo.security;

import com.bakigoal.securitydemo.model.EntityStatus;
import com.bakigoal.securitydemo.model.Role;
import com.bakigoal.securitydemo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AuthorizedUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private final boolean enabled;
    private final Date lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @Override
    public String toString() {
        return "AuthorizedUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                ", authorities=" + authorities +
                '}';
    }

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
                mapToGrantedAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
