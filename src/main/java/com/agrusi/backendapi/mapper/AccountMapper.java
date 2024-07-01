package com.agrusi.backendapi.mapper;

import com.agrusi.backendapi.dto.response.account.AccountBasicResponseDto;
import com.agrusi.backendapi.dto.response.account.AccountFullResponseDto;
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

    @Mapping(source = "verified", target = "accountVerified")
    AccountRegistrationResponseDto toAccountRegistrationResponseDto(Account account);

    @Mapping(source = "verified", target = "accountVerified")
    @Mapping(source = "dateCreated", target = "createdAt")
    @Mapping(source = "lastUpdated", target = "updatedAt")
    AccountFullResponseDto toAccountFullResponseDto(Account account);

    @Mapping(source = "verified", target = "accountVerified")
    AccountBasicResponseDto toAccountBasicResponseDto(Account account);
}
