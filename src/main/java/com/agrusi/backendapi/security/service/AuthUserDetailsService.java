package com.agrusi.backendapi.security.service;

import com.agrusi.backendapi.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AuthUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AuthUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return accountRepository
                .findAccountByEmail(email)
                .map(com.agrusi.backendapi.security.service.AuthUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Account not found with email: " + email)
                );
    }
}
