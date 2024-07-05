package com.agrusi.backendapi.service.impl;

import com.agrusi.backendapi.dto.request.account.AccountPutGeneralDto;
import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
import com.agrusi.backendapi.exception.account.AccountWithPublicIdNotFoundException;
import com.agrusi.backendapi.mapper.AccountMapper;
import com.agrusi.backendapi.model.Account;
import com.agrusi.backendapi.repository.AccountRepository;
import com.agrusi.backendapi.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            AccountMapper accountMapper
    ) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountFullResponseDto getAccountByPublicId(UUID publicId) {

        Account account = accountRepository.findAccountByPublicId(publicId)
                .orElseThrow(() -> new AccountWithPublicIdNotFoundException(publicId));

        return accountMapper.toAccountFullResponseDto(account);
    }

    @Override
    @Transactional
    public AccountBasicResponseDto updateAccountBasicInfoByPublicIdPut(
            UUID publicId,
            AccountPutGeneralDto updateDto
    ) {
        Account account = accountRepository.findAccountByPublicId(publicId)
                .orElseThrow(() -> new AccountWithPublicIdNotFoundException(publicId));

        account.setFirstName(updateDto.getFirstName());
        account.setLastName(updateDto.getLastName());

        return accountMapper.toAccountBasicResponseDto(account);
    }

    @Override
    @Transactional
    public void deleteAccountByPublicId(UUID publicId) {

        Account account = accountRepository.findAccountByPublicId(publicId)
                .orElseThrow(() -> new AccountWithPublicIdNotFoundException(publicId));

        // Get currently authenticated user's email...
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();

        // TODO --> IMPLEMENT "SecurityException" WHEN USER NOT ALLOWED TO DELETE ACCOUNT !!!

        // Check if the authenticated user is the owner of the account
        if (!account.getEmail().equals(authenticatedUser.getName())) { // Email in this case !!!
            throw new SecurityException("You are not authorized to delete this account");
        }

        account.getAuthorities().clear();
        accountRepository.save(account);

        accountRepository.delete(account);
    }
}