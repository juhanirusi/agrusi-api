package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.request.auth.LoginDto;
import com.agrusi.backendapi.dto.request.auth.RegisterDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.dto.response.auth.LoginResponseDto;
import com.agrusi.backendapi.enums.EAccountRole;
import com.agrusi.backendapi.enums.EAreaUnit;
import com.agrusi.backendapi.exception.auth.AccountWithEmailAlreadyExistException;
import com.agrusi.backendapi.mapper.AccountMapper;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.AccountPreferences;
import com.agrusi.backendapi.model.Role;
import com.agrusi.backendapi.model.UserProfile;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.repository.RoleRepository;
import com.agrusi.backendapi.security.service.TokenService;
import com.agrusi.backendapi.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AccountMapper accountMapper;

    public AuthServiceImpl(
            AccountRepository accountRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            AccountMapper accountMapper
    ) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public AccountRegistrationResponseDto registerNewAccount(RegisterDto registerDto) {

        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            throw new AccountWithEmailAlreadyExistException();
        }

        Account account = new Account();
        UserProfile userProfile = new UserProfile();
        AccountPreferences accountPreferences = new AccountPreferences();

        Role userRole = roleRepository.findByAuthority(EAccountRole.USER)
                .orElseThrow(() -> new RuntimeException("Role not found."));

        account.setFirstName(registerDto.getFirstName());
        account.setLastName(registerDto.getLastName());
        account.setEmail(registerDto.getEmail());
        account.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        account.setAuthorities(Set.of(userRole));
        account.setUserProfile(userProfile);
        account.setAccountPreferences(accountPreferences);

        userProfile.setAccount(account);

        accountPreferences.setAccount(account);
        accountPreferences.setLanguage(registerDto.getLanguage());
        accountPreferences.setCurrency(registerDto.getCurrency());
        accountPreferences.setTimeZone(registerDto.getTimeZone());
        accountPreferences.setFieldAreaUnit(EAreaUnit.valueOf(registerDto.getFieldAreaUnit().toUpperCase()));

        accountRepository.save(account);

        return accountMapper.toAccountRegistrationResponseDto(account);
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()
                )
        );

        System.out.printf(authentication.getPrincipal().toString());
        System.out.printf(authentication.getAuthorities().toString());

        String token = tokenService.generateJwt(authentication);
        
        return new LoginResponseDto(loginDto.getEmail(), token);
    }
}
