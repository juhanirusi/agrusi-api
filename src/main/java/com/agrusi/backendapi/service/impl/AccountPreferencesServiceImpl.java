package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.request.accountPreferences.AccountPreferencesPatchDto;
import com.agrusi.backendapi.dto.response.accountPreferences.AccountPreferencesResponseDto;
import com.agrusi.backendapi.exception.account.AccountWithPublicIdNotFoundException;
import com.agrusi.backendapi.mapper.AccountPreferencesMapper;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.model.AccountPreferences;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.service.AccountPreferencesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountPreferencesServiceImpl implements AccountPreferencesService {

    private final AccountRepository accountRepository;
    private final AccountPreferencesMapper accountPreferencesMapper;

    public AccountPreferencesServiceImpl(
            AccountRepository accountRepository,
            AccountPreferencesMapper accountPreferencesMapper
    ) {
        this.accountRepository = accountRepository;
        this.accountPreferencesMapper = accountPreferencesMapper;
    }

    @Override
    public AccountPreferences fetchAccountPreferencesByAccountPublicId(
            UUID accountPublicId
    ) {
        Account account = accountRepository.findAccountByPublicId(accountPublicId)
                .orElseThrow(() -> new AccountWithPublicIdNotFoundException(accountPublicId));

        return account.getAccountPreferences();
    }

    @Override
    public AccountPreferencesResponseDto getAccountPreferencesByAccountPublicId(
            UUID accountPublicId
    ) {
        Account account = accountRepository.findAccountByPublicId(accountPublicId)
                .orElseThrow(() -> new AccountWithPublicIdNotFoundException(accountPublicId));

        return accountPreferencesMapper.toAccountPreferencesResponseDto(account);
    }

    @Override
    @Transactional
    public AccountPreferencesResponseDto updateAccountPreferencesByAccountPublicIdPatch(
            UUID accountPublicId, AccountPreferencesPatchDto updateDto
    ) {
        Account account = accountRepository.findAccountByPublicId(accountPublicId)
                .orElseThrow(() -> new AccountWithPublicIdNotFoundException(accountPublicId));

        AccountPreferences accountPreferences = account.getAccountPreferences();

        // Use Optional to avoid null checks
        Optional.ofNullable(updateDto.getLanguage()).ifPresent(accountPreferences::setLanguage);
        Optional.ofNullable(updateDto.getCurrency()).ifPresent(accountPreferences::setCurrency);
        Optional.ofNullable(updateDto.getTimeZone()).ifPresent(accountPreferences::setTimeZone);

        accountRepository.save(account);

        return accountPreferencesMapper.toAccountPreferencesResponseDto(account);
    }

    // Get current user's account preferences...

    @Override
    public AccountPreferences getCurrentAuthenticatedUserPreferences() {

        // Get the current authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // TODO --> CREATE SOME CUSTOM EXCEPTION !!!
            System.out.println("CREATE SOME CUSTOM EXCEPTION !!!");
        }

        assert authentication != null;

        // Get what account is calling this service method from our custom JWT
        // claim and fetch the account preferences for that account with the
        // "fetchAccountPreferencesByAccountPublicId" method...

        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID accountPublicId = UUID.fromString(jwt.getClaim("accountPublicId"));

        // Fetch and return cached preferences
        return fetchAccountPreferencesByAccountPublicId(accountPublicId);
    }
}
