package com.agrusi.backendapi.mapper;

import com.agrusi.backendapi.dto.response.accountPreferences.AccountPreferencesResponseDto;
import com.agrusi.backendapi.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountPreferencesMapper {

    @Mapping(source = "accountPreferences.language", target = "language")
    @Mapping(source = "accountPreferences.currency", target = "currency")
    @Mapping(source = "accountPreferences.timeZone", target = "timeZone")
    @Mapping(source = "accountPreferences.fieldAreaUnit", target = "fieldAreaUnit")
    AccountPreferencesResponseDto toAccountPreferencesResponseDto(Account account);
}
