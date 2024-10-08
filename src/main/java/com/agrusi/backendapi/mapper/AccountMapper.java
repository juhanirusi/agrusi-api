package com.agrusi.backendapi.mapper;

import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountPreferencesResponseDto;
import com.agrusi.backendapi.dto.response.auth.AccountRegistrationResponseDto;
import com.agrusi.backendapi.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/*
* componentModel = "spring" --> The generated mapper is a Spring bean
* and can be retrieved via @Autowired
*/

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountRegistrationResponseDto toAccountRegistrationResponseDto(Account account);

    @Mapping(source = "dateCreated", target = "createdAt")
    @Mapping(source = "lastUpdated", target = "updatedAt")
    AccountFullResponseDto toAccountFullResponseDto(Account account);

    AccountBasicResponseDto toAccountBasicResponseDto(Account account);

    @Mapping(source = "accountPreferences.language", target = "language")
    @Mapping(source = "accountPreferences.currency", target = "currency")
    @Mapping(source = "accountPreferences.timeZone", target = "timeZone")
    AccountPreferencesResponseDto toAccountPreferencesResponseDto(Account account);
}
