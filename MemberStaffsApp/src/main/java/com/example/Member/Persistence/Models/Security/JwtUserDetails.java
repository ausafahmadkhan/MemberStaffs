package com.example.Member.Persistence.Models.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class JwtUserDetails implements UserDetails
{
    private String username;
    private String password;
    private Boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(UserDAO userDAO)
    {
        this.username = userDAO.getUsername();
        this.password = userDAO.getPassword();
        this.isActive = userDAO.getIsActive();
        this.authorities = userDAO.getRoles()
                                  .stream()
                                  .map(role -> new SimpleGrantedAuthority(role.getValue()))
                                  .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public String toString()
    {
        return this.username + "\t" + this.password + "\t" + this.isActive + "\t" + this.authorities;
    }
}
