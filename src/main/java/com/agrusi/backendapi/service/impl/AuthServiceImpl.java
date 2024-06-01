package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.request.LoginDto;
import com.agrusi.backendapi.dto.request.RegisterDto;
import com.agrusi.backendapi.dto.response.LoginResponseDto;
import com.agrusi.backendapi.dto.response.RegisterAccountResponseDto;
import com.agrusi.backendapi.exception.auth.AccountWithEmailAlreadyExistException;
import com.agrusi.backendapi.mapper.AccountMapper;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.EAccountRole;
import com.agrusi.backendapi.model.Role;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.repository.RoleRepository;
import com.agrusi.backendapi.security.service.TokenService;
import com.agrusi.backendapi.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
//@Transactional ???
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AccountMapper accountMapper;

    public AuthServiceImpl(
            AccountRepository accountRepository, RoleRepository roleRepository,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
            TokenService tokenService, AccountMapper accountMapper
    ) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.accountMapper = accountMapper;
    }

    @Override
    public RegisterAccountResponseDto registerNewAccount(RegisterDto registerDto) {

        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            throw new AccountWithEmailAlreadyExistException(
                    "Account with this email already exists"
            );
        }

        Account newAccount = new Account();

        Role userRole = roleRepository.findByAuthority(EAccountRole.USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        newAccount.setFirstName(registerDto.getFirstName());
        newAccount.setLastName(registerDto.getLastName());
        newAccount.setEmail(registerDto.getEmail());
        newAccount.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newAccount.setAuthorities(Set.of(userRole));

        accountRepository.save(newAccount);

        return accountMapper.INSTANCE.toRegisterAccountResponseDto(newAccount);
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()
                )
        );

        String token = tokenService.generateJwt(authentication);

//        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

        return new LoginResponseDto(loginDto.getEmail(), token);
    }
}
