package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.RegisterDto;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;

    public AuthServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public String register(RegisterDto registerDto) {

        Account account = new Account();

        account.setEmail(registerDto.getEmail());
        account.setPassword(registerDto.getPassword());

        accountRepository.save(account);

        return "User registered successfully!";
    }
}
