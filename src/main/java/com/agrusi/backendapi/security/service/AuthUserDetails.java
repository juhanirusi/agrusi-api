package com.agrusi.backendapi.security.service;

import com.agrusi.backendapi.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class AuthUserDetails implements UserDetails {

    private final Account account;

    public AuthUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return account.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    public UUID getPublicId() {
        return account.getPublicId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // IMPLEMENT LOGIC IN ENTITY AND WHOLE APPLICATION !!!
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // IMPLEMENT LOGIC IN ENTITY AND WHOLE APPLICATION !!!
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // IMPLEMENT LOGIC IN ENTITY AND WHOLE APPLICATION !!!
    }

    @Override
    public boolean isEnabled() {
        return true; // account.isVerified() ???;
    }
}
